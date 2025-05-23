import socket
import time

server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server_socket.bind(("0.0.0.0", 65432))
server_socket.listen(1)
print("Server listening...")

conn, addr = server_socket.accept()
print(f"Connected by {addr}")

buffer_size = 4096
total_bytes = 0
start_time = time.time()

while True:
    data = conn.recv(buffer_size)
    if not data:
        break
    total_bytes += len(data)

end_time = time.time()

conn.send(b"Transfer complete")
conn.close()
server_socket.close()

duration = end_time - start_time
speed_bps = total_bytes / duration

print(f"Received: {total_bytes} bytes in {duration:.2f} seconds.")
print(f"Speed: {speed_bps:.2f} B/s ({speed_bps / 1024:.2f} KiB/s, {speed_bps / 1024 / 1024:.2f} MiB/s)")
