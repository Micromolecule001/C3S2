import socket
import threading
import os
import mimetypes

DOCUMENT_ROOT = '.'  
DEFAULT_FILE = 'index.html'
HOST = '192.168.0.104'
PORT = 8000
BUFFER_SIZE = 1024

#               ssh -o StrictHostKeyChecking=no student@31.134.121.208 -p 10022 -N -R 15055:192.168.0.102:8000

def handle_client(connection, address):
    try:
        request = connection.recv(BUFFER_SIZE).decode('utf-8')
        if not request:
            return

        # Розбір першого рядка HTTP-запиту
        request_line = request.splitlines()[0]
        parts = request_line.split()
        if len(parts) != 3:
            return

        method, uri, _ = parts

        if method != 'GET':
            send_response(connection, 405, 'Method Not Allowed', 'text/plain', 'Method Not Allowed')
            return

        # Обробка URI
        if uri == '/':
            filepath = os.path.join(DOCUMENT_ROOT, DEFAULT_FILE)
        else:
            filepath = os.path.join(DOCUMENT_ROOT, uri.lstrip('/'))

        if os.path.isfile(filepath):
            with open(filepath, 'rb') as f:
                content = f.read()
            content_type = mimetypes.guess_type(filepath)[0] or 'application/octet-stream'
            send_response(connection, 200, 'OK', content_type, content)
        else:
            send_response(connection, 404, 'Not Found', 'text/html', b'<h1>404 Not Found</h1>')

    except Exception as e:
        print(f'Error: {e}')
    finally:
        connection.close()

def send_response(conn, status_code, status_text, content_type, content):
    headers = [
        f'HTTP/1.1 {status_code} {status_text}',
        f'Content-Type: {content_type}',
        f'Content-Length: {len(content)}',
        'Connection: close',
        '', ''
    ]
    header_data = '\r\n'.join(headers).encode('utf-8')
    conn.sendall(header_data + content)

def run_server():
    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as server_socket:
        server_socket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
        server_socket.bind((HOST, PORT))
        server_socket.listen(5)
        print(f'Serving HTTP on {HOST} port {PORT} ...')

        while True:
            client_conn, client_addr = server_socket.accept()
            threading.Thread(target=handle_client, args=(client_conn, client_addr)).start()

if __name__ == '__main__':
    run_server()
