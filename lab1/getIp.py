import requests

def get_public_ip():
    response = requests.get("https://api.ipify.org")  # Або будь-який інший подібний сервіс
    if response.status_code == 200:
        return response.text.strip()
    else:
        raise Exception("Не вдалося отримати IP адресу")

# Перевірка:
my_ip = get_public_ip()
print(f"Моя публічна IP-адреса: {my_ip}")

