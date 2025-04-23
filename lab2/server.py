import socket

# 1. Створення сокету
sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

# 2. Прив'язка до порту
server_address = ('0.0.0.0', 12345)
sock.bind(server_address)

print("Сервер запущено. Очікування повідомлень...")

try:
    while True:
        # 4. Отримання повідомлення
        data, addr = sock.recvfrom(1024)
        print(f"Отримано від {addr}: {data.decode()}")

        # Відповідь назад
        message = f"Прийнято: {data.decode()}"
        sock.sendto(message.encode(), addr)

except KeyboardInterrupt:
    print("Закриття сервера...")

finally:
    sock.close()

