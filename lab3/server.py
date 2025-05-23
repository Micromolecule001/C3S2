import socket
import time

HOST = '0.0.0.0'
PORT = 65432

server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server_socket.bind((HOST, PORT))
server_socket.listen(1)

print(f"Server is listening on {HOST}:{PORT}...")
conn, addr = server_socket.accept()
print(f"Connected by {addr}")

total_received = 0
start_time = time.time()

while True:
    data = conn.recv(4096)
    if not data:
        break
    total_received += len(data)

end_time = time.time()
duration = end_time - start_time

conn.shutdown(socket.SHUT_RDWR)
conn.close()
server_socket.close()

if duration > 0:
    speed_bps = total_received / duration
    print(f"Received {total_received} bytes in {duration:.2f} seconds.")
    print(f"Speed: {speed_bps:.2f} B/s ({speed_bps/1024:.2f} KiB/s, {speed_bps/1024/1024:.2f} MiB/s)")
else:
    print("Duration was too short to measure speed.")
