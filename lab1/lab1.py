import requests
from ftplib import FTP
import ipaddress

# Етап 1: Отримуємо публічну IP
ip = requests.get('https://api.ipify.org').text
print(f"Ваша IP: {ip}")

# Етап 2: Завантажуємо файл делегацій і шукаємо відповідність
ftp = FTP('ftp.ripe.net')
ftp.login()
with open('delegated-ripencc-latest', 'wb') as f:
    ftp.retrbinary('RETR /pub/stats/ripencc/delegated-ripencc-latest', f.write)
ftp.quit()

ip_int = int(ipaddress.IPv4Address(ip))
with open('delegated-ripencc-latest', 'r') as f:
    for line in f:
        if line.startswith('ripencc') and 'ipv4' in line:
            parts = line.split('|')
            netaddr, size = parts[3], parts[4]
            # Пропускаємо невалідні IP-адреси
            if not netaddr.replace('.', '').isdigit():
                continue
            try:
                size = int(size)
                mask = 32 - (size.bit_length() - 1)
                net = ipaddress.IPv4Network(f"{netaddr}/{mask}")
                if ip_int & int(net.netmask) == int(net.network_address):
                    print(f"Делегація: {line.strip()}")
                    break
            except ValueError:
                continue

