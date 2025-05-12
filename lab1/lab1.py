import requests
from ftplib import FTP
import io
import ipaddress
import math

def get_public_ip():
    response = requests.get("https://api.ipify.org")
    response.raise_for_status()
    return response.text.strip()

def find_delegation(ip_str, delegation_lines, rir_name):
    ip = ipaddress.IPv4Address(ip_str)
    for line in delegation_lines:
        parts = line.strip().split('|')
        if len(parts) < 7 or parts[2] != 'ipv4':
            continue
        try:
            net_start = parts[3]
            count = int(parts[4])

            # Обчислюємо кінець діапазону
            start_ip = int(ipaddress.IPv4Address(net_start))
            end_ip = start_ip + count - 1
            end_ip_addr = ipaddress.IPv4Address(end_ip)

            # Створюємо список мереж, що покривають цей діапазон
            networks = ipaddress.summarize_address_range(
                ipaddress.IPv4Address(net_start),
                end_ip_addr
            )

            # Перевірка входження IP в одну з мереж
            for network in networks:
                if ip in network:
                    return rir_name, line
        except Exception:
            continue
    return None, None

def download_delegation_file(ftp_host, ftp_path, filename):
    ftp = FTP(ftp_host)
    ftp.login()
    ftp.cwd(ftp_path)
    file_buffer = io.BytesIO()
    ftp.retrbinary(f"RETR {filename}", file_buffer.write)
    ftp.quit()
    file_buffer.seek(0)
    return file_buffer.read().decode('utf-8').splitlines()

def check_all_rirs(ip_str):
    rirs = [
        {"name": "RIPE NCC", "host": "ftp.ripe.net", "path": "/pub/stats/ripencc", "file": "delegated-ripencc-latest"}
    ]

    for rir in rirs:
        try:
            lines = download_delegation_file(rir["host"], rir["path"], rir["file"])
            rir_name, result = find_delegation(ip_str, lines, rir["name"])
            if result:
                return rir_name, result
        except Exception as e:
            print(f"⚠️ Помилка з {rir['name']}: {e}")
    return None, None

# --- Основна програма ---
my_ip = get_public_ip()
print(f"✅ Ваша публічна IP-адреса: {my_ip}")

print(" Починаю перевірку по всіх RIR...")
rir_name, matching_line = check_all_rirs(my_ip)

if matching_line:
    print(f"ℹ️ Інформація про делегацію:\n{matching_line}")
else:
    print("\n❌ IP-адреса не знайдена в жодній делегації.")

