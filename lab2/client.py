import socket
import sys
import os

# Set console encoding to UTF-8
if sys.platform == "win32":
    os.system("chcp 65001")

sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
server_address = ('192.168.0.104', 12345)
sock.settimeout(5)

try:
    while True:
        message = input("Введи повідомлення для відправки (або 'exit'): ")
        if message == "exit":
            break
        sock.sendto(message.encode('utf-8'), server_address)
        try:
            data, addr = sock.recvfrom(1024)
            if addr == server_address:
                print("Сервер відповів:", data.decode('utf-8'))
            else:
                print("Отримано від невідомого джерела")
        except socket.timeout:
            print("Сервер не відповів")
except Exception as e:
    print(f"Помилка: {e}")
finally:
    sock.close()
