# client.py
import socket

SERVER_IP = '192.168.0.104'  # заміни на IP сервера (Arch Linux)
PORT = 65432

client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
client_socket.connect((SERVER_IP, PORT))

message = "Hello from Windows VM!"
client_socket.sendall(message.encode())

response = client_socket.recv(1024)
print(f"Server replied: {response.decode()}")

client_socket.shutdown(socket.SHUT_RDWR)
client_socket.close()

