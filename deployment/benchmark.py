import os
import time
import json
import requests
from kill_service import kill_service
from get_services import get_services

CONFIG_FILES_DIR = "app_config_files"
TIMEOUT = 60 # s


def get_image(path):
    with open(path) as f:
        return json.load(f)["container"]["docker"]["image"]


def benchmark_start(app_id, n_apps=1, timeout=TIMEOUT):
    start = time.time()
    stop = time.time()
    while True:
        services = get_services()[app_id]
        if n_apps == len(services):
            break

        stop = time.time()
        if stop - start > timeout:
            raise RuntimeError("Timeout reached ({0}s)".format(timeout))
    return stop - start


def benchmark_restart(app_id):
    old_services = get_services()[app_id]
    ip = old_services[0]
    image = get_image(os.path.join(CONFIG_FILES_DIR, app_id))
    containers = get_services(ip)
    container = containers[image][0]

    print("Killing container {0} on {1}...".format(container, ip))
    kill_service(ip, container)

    print("Restarting client...")
    try:
        return benchmark_start(app_id, len(old_services))
    except RuntimeError as e:
        print(e)
        return


if __name__ == "__main__":
    print("Client restarted in", benchmark_restart("/client"), "s")
