# server.py
import socket

HOST = '0.0.0.0'  # приймати з'єднання з усіх інтерфейсів
PORT = 65432

server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server_socket.bind((HOST, PORT))
server_socket.listen(1)  # максимум 1 підключення в черзі

print(f"Server is listening on {HOST}:{PORT}...")

conn, addr = server_socket.accept()
print(f"Connected by {addr}")

while True:
    data = conn.recv(1024)
    if not data:
        break
    print(f"Received: {data.decode()}")
    conn.sendall(b"Data received")  # відповідь

conn.shutdown(socket.SHUT_RDWR)
conn.close()
server_socket.close()

