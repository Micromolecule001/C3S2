from ftplib import FTP
import io

def download_delegation_file():
    ftp = FTP("ftp.ripe.net")
    ftp.login()
    ftp.cwd("/pub/stats/ripencc")

    file_buffer = io.BytesIO()
    ftp.retrbinary("RETR delegated-ripencc-latest", file_buffer.write)
    ftp.quit()

    file_buffer.seek(0)
    return file_buffer.read().decode('utf-8').splitlines()

# Перевірка:
delegation_lines = download_delegation_file()
print(f"Завантажено {len(delegation_lines)} рядків з делегаційного файлу")

