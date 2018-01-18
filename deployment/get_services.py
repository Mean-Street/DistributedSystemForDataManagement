import requests
import json
from get_instances import get_instances, get_public_ip, get_private_ip
from tools import ssh


def get_services(public_ip=None):
    if public_ip is not None:
        p = ssh(public_ip, "sudo docker ps") 
        p.wait()
        return

    priv_to_pub = {}
    for slave in get_instances(is_slave=True):
        priv_to_pub[get_private_ip(slave)] = get_public_ip(slave)


    masters = get_instances(is_slave=False)
    ip = get_public_ip(masters[0])
    r = requests.get('http://' + ip + ':8080/v2/apps')

    print("Public ip", "|", "Service")
    for service in r.json()["apps"]:
        r = requests.get('http://' + ip + ':8080/v2/apps/' + service["id"] + "/tasks")
        for task in r.json()["tasks"]:
            print(priv_to_pub[task["host"]], "|", service["id"])
        


if __name__ == "__main__":
    import sys
    try:
        pub_ip = sys.argv[1]
    except IndexError:
        get_services()
    else:
        get_services(pub_ip)
