import socket
import time

client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
client_socket.connect(("192.168.0.104", 65432))

buffer_size = 4096
total_data = 100 * 1024 * 1024  # 100 MiB
data = b'x' * buffer_size

sent = 0
start_time = time.time()

while sent < total_data:
    client_socket.send(data)
    sent += len(data)

client_socket.shutdown(socket.SHUT_WR)

response = client_socket.recv(1024)
end_time = time.time()

client_socket.close()

duration = end_time - start_time
speed_bps = total_data / duration

print(f"Sent: {total_data} bytes in {duration:.2f} seconds.")
print(f"Speed: {speed_bps:.2f} B/s ({speed_bps / 1024:.2f} KiB/s, {speed_bps / 1024 / 1024:.2f} MiB/s)")
