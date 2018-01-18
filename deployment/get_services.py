import requests
import json
import subprocess as sp
from collections import defaultdict
from get_instances import get_instances, get_public_ip, get_private_ip
from tools import ssh


def get_services(public_ip=None):
    if public_ip is not None:
        p = ssh(public_ip, "sudo docker ps", stdout=sp.PIPE) 
        p.wait()
        output = p.stdout.read().decode("utf-8").split("\n")[1:]
        containers = defaultdict(list)
        for line in output:
            line = line.split()
            try:
                containers[line[1]].append(line[0])
            except IndexError:
                pass
        return containers

    priv_to_pub = {}
    for slave in get_instances(is_slave=True):
        priv_to_pub[get_private_ip(slave)] = get_public_ip(slave)


    masters = get_instances(is_slave=False)
    ip = get_public_ip(masters[0])
    r = requests.get('http://' + ip + ':8080/v2/apps')

    services = defaultdict(list)
    for service in r.json()["apps"]:
        r = requests.get('http://' + ip + ':8080/v2/apps/' + service["id"] + "/tasks")
        for task in r.json()["tasks"]:
            if task["state"] != "TASK_RUNNING":
                continue
            services[service["id"]].append(priv_to_pub[task["host"]])
    return services


if __name__ == "__main__":
    import sys
    try:
        pub_ip = sys.argv[1]
    except IndexError:
        print("Public ip", "|", "Service")
        for service, ips in get_services().items():
            for ip in ips:
                print(ip, "|", service)
    else:
        print(json.dumps(get_services(pub_ip), indent=2))
