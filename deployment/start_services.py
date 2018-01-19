import os
import time
import requests
import json
import subprocess as sp
import config as cfg
from get_instances import get_instances, get_public_ip, get_private_ip
from benchmark import benchmark_start

LOG_PATH = "logs.txt"

def print_header(text):
    print("\n")
    print("-------------------")
    print(text)
    print("-------------------")
    print("\n")


def prepare_spark_cassandra_connection(filepath, master_ips):
    with open(filepath, "r") as f:
        data = json.load(f)
    data["env"]["CASSANDRA_HOST"] = ','.join(master_ips)
    with open(filepath, "w") as f:
        json.dump(data, f, indent=4)

    #  cmd = ''
    #  for ip in master_ips:
    #      cmd += ip + ","
    #  cmd = cmd[:-1]
    #  command = ["sed", "-i", "-e", "s/\"CASSANDRA_SEEDS\":.*/\"CASSANDRA_SEEDS\":\"" + cmd + "\",/g", "app_config_files/cassandra2.json"]
    #  sp.call(command)
    #  command = ["sed", "-i", "-e", "s/\"CASSANDRA_LISTEN_ADDRESS\":.*/\"CASSANDRA_LISTEN_ADDRESS\":\"" + cmd + "\"/g", "app_config_files/cassandra2.json"]
    #  sp.call(command)


def start_service(ip, filepath):
    with open(filepath) as f:
        data = json.load(f)
        print_header("Starting " + data["id"])
        resp = requests.post('http://' + ip + ':8080/v2/apps', json=data)
        if resp.status_code // 100 != 2:
            raise RuntimeError(str(resp.status_code) + ": " + resp.text)


def start_services():
    instances = get_instances(is_slave=False)
    ip = get_public_ip(instances[0])
    times = {}

    # Set masters IP for Spark to contact Cassandra nodes
    master_ips = [get_private_ip(instance) for instance in get_instances(is_slave=False)]
    prepare_spark_cassandra_connection("app_config_files/spark-temperature.json", master_ips)
    prepare_spark_cassandra_connection("app_config_files/spark-tweet.json", master_ips)

    for service in ["zookeeper", "kafka", "cassandra", "spark-temperature",
                    "spark-tweet", "akka-temperature", "akka-tweet", "client"]:
        fpath = "app_config_files/" + service + ".json"
        start_service(ip, fpath)
        with open(fpath) as f:
            instances = json.load(f)["instances"]
        try:
            time = benchmark_start("/" + service, n_apps=instances)
        except RuntimeError:
            time = None
        times["/" + service] = time
    return times


if __name__ == "__main__":
    print(json.dumps(start_services(), indent=2))
