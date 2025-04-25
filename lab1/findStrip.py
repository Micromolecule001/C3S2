import ipaddress

def find_delegation(ip_str, delegation_lines):
    ip = int(ipaddress.IPv4Address(ip_str))

    for line in delegation_lines:
        parts = line.strip().split('|')
        if len(parts) < 7:
            continue
        if parts[2] != 'ipv4':
            continue
        
        net_addr_str = parts[3]
        count = int(parts[4])
        
        # Визначаємо довжину префікса
        import math
        prefix_length = 32 - int(math.log2(count))
        network = ipaddress.IPv4Network(f"{net_addr_str}/{prefix_length}", strict=False)

        if ip in network:
            return line

    return None

# Перевірка:
matching_line = find_delegation(my_ip, delegation_lines)
if matching_line:
    print(f"IP-адреса належить до делегації:\n{matching_line}")
else:
    print("IP-адреса не знайдена в делегаціях")

