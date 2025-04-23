import socket

sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

# IP-адреса сервера
server_address = ('127.0.0.1', 12345)

try:
    while True:
        message = input("Введи повідомлення для відправки (або 'exit'): ")
        if message == "exit":
            break

        # 3. Відправлення
        sock.sendto(message.encode(), server_address)

        # 4. Отримання відповіді
        data, _ = sock.recvfrom(1024)
        print("Сервер відповів:", data.decode())

finally:
    sock.close()

