import time
import json
import requests
from kill_service import kill_service
from get_services import get_services

CLIENT_ID = "/client"
CLIENT_CONFIG_FILE = "app_config_files/client.json"
TIMEOUT = 60 # s


def get_client_image():
    with open(CLIENT_CONFIG_FILE) as f:
        return json.load(f)["container"]["docker"]["image"]


def benchmark():
    old_clients = get_services()[CLIENT_ID]
    client_ip = old_clients[0]
    clients = []
    client_image = get_client_image()
    containers = get_services(client_ip)
    client_container = containers[client_image][0]

    print("Killing container {0} on {1}...".format(client_container, client_ip))
    kill_service(client_ip, client_container)

    print("Restarting client...")
    start = time.time()
    while True:
        clients = get_services()[CLIENT_ID]
        if len(old_clients) == len(clients):
            break

        stop = time.time()
        if stop - start > TIMEOUT:
            print("Timeout reached ({0}s)".format(TIMEOUT))
            return

    try:
        spawned_ip = list(set(clients) - set(old_clients))[0]
    except IndexError: # Same ip as before
        spawned_ip = old_clients[0]

    print("New client:", spawned_ip, "Trying to request it...")
    r = requests.get("http://" + spawned_ip + ":8080/example")
    if r.status_code != 200:
        print("Cannot reach new client")
    else:
        print("Client requested with success at /example")

    return stop - start


if __name__ == "__main__":
    print("Client restarted in", benchmark(), "s")
