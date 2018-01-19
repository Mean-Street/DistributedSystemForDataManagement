import requests
import json
from get_instances import get_instances, get_public_ip


def stop_services():
    instances = get_instances(is_slave=False)
    ip = get_public_ip(instances[0])
    services = [
        "app_config_files/client.json",
        "app_config_files/akka-tweet.json",
        "app_config_files/akka-temperature.json",
        "app_config_files/spark-tweet.json",
        "app_config_files/spark-temperature.json",
        "app_config_files/cassandra.json",
        "app_config_files/kafka.json",
        "app_config_files/zookeeper.json",
    ]
    for service in services:
        with open(service) as f:
            service_id = json.load(f)["id"]

        print("Stopping", service_id)
        resp = requests.delete('http://' + ip + ':8080/v2/apps/' + service_id)
        if resp.status_code != 200:
            print(resp.json()['message'])


if __name__ == "__main__":
    stop_services()
