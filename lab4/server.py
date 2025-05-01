# server.py

import socket
import threading
import os
import mimetypes

DOCUMENT_ROOT = os.getcwd()
HOST = '0.0.0.0'  # слухаємо на всіх інтерфейсах
PORT = 8000       # порт (для тестування не 80)

# Обробка одного клієнта
def handle_client(client_socket):
    try:
        request = b""
        while b"\r\n\r\n" not in request:
            chunk = client_socket.recv(1024)
            if not chunk:
                break
            request += chunk

        lines = request.decode().split("\r\n")
        if len(lines) == 0:
            return

        request_line = lines[0]
        parts = request_line.split()
        if len(parts) != 3:
            return

        method, uri, version = parts

        if method != 'GET':
            response = f"{version} 405 Method Not Allowed\r\n\r\n"
            client_socket.send(response.encode())
            return

        if uri == '/':
            path = os.path.join(DOCUMENT_ROOT, 'index.html')
        else:
            # Відрізаємо перший слеш
            path = os.path.join(DOCUMENT_ROOT, uri.lstrip('/'))

        if os.path.isfile(path):
            with open(path, 'rb') as f:
                body = f.read()
            content_type, _ = mimetypes.guess_type(path)
            if not content_type:
                content_type = 'application/octet-stream'

            response_headers = (
                f"{version} 200 OK\r\n"
                f"Content-Type: {content_type}\r\n"
                f"Content-Length: {len(body)}\r\n"
                f"Connection: close\r\n"
                f"\r\n"
            ).encode()
            client_socket.send(response_headers + body)

        else:
            body = b"<h1>404 Not Found</h1>"
            response_headers = (
                f"{version} 404 Not Found\r\n"
                f"Content-Type: text/html\r\n"
                f"Content-Length: {len(body)}\r\n"
                f"Connection: close\r\n"
                f"\r\n"
            ).encode()
            client_socket.send(response_headers + body)

    finally:
        client_socket.close()

# Головний серверний цикл
def start_server():
    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server_socket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    server_socket.bind((HOST, PORT))
    server_socket.listen(5)
    print(f"Listening on {HOST}:{PORT}...")

    while True:
        client_sock, addr = server_socket.accept()
        print(f"Accepted connection from {addr}")
        client_thread = threading.Thread(target=handle_client, args=(client_sock,))
        client_thread.start()

if __name__ == "__main__":
    start_server()
