import os
import requests
import json
import subprocess as sp
import config as cfg
from get_instances import get_instances, get_public_ip, get_private_ip

LOG_PATH = "logs.txt"

def print_header(text):
    print("\n")
    print("-------------------")
    print(text)
    print("-------------------")
    print("\n")


def prepare_cassandra(master_ips): #Rewrites the cassandra.json file to include master ips
    cmd = ''
    for ip in master_ips:
        cmd += ip + ","
    cmd = cmd[:-1]
    command = ["sed", "-i", "-e", "s/\"CASSANDRA_SEEDS\":.*/\"CASSANDRA_SEEDS\":\"" + cmd + "\"/g", "app_config_files/cassandra.json"]
    sp.call(command)


def start_service(ip, filepath):
    with open(filepath) as f:
        data = json.load(f)
        resp = requests.post('http://' + ip + ':8080/v2/apps', json=data)


def start_services():
    instances = get_instances(is_slave=False)
    ip = get_public_ip(instances[0])
    master_ips = [get_private_ip(instance) for instance in get_instances(is_slave=False)]
    services = []

    prepare_cassandra(master_ips)

    #List all services here
#    services.append("app_config_files/hello_world.json") #Fichier de test
#    services.append("app_config_files/zookeeper.json")
#    services.append("app_config_files/akka_weatherfinder.json")
#    services.append("app_config_files/cassandra.json")
#    services.append("app_config_files/kafka_temperature.json")
#    services.append("app_config_files/spark_temperature.json")


    for service in services:
        print_header("Starting : " + service)
        start_service(ip, service)
        time.sleep(20)

if __name__ == "__main__":
    start_services()
