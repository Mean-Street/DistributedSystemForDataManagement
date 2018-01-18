from tools import ssh


def kill_service(pub_ip, service):
    p = ssh(pub_ip, "sudo docker stop " + service)
    p.wait()


if __name__ == "__main__":
    import sys
    try:
        pub_ip = sys.argv[1]
        service = sys.argv[2]
    except IndexError:
        print("Usage: python3 kill_service.py <public-ip> <container-id-or-name>")
        sys.exit(1)
    kill_service(pub_ip, service)
