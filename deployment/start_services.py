import os
import time
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
    command = ["sed", "-i", "-e", "s/\"CASSANDRA_LISTEN_ADDRESS\":.*/\"CASSANDRA_SEEDS\":\"" + cmd + "\"/g", "app_config_files/cassandra.json"]
    sp.call(command)


def start_service(ip, filepath):
    print_header("Starting " + filepath)
    with open(filepath) as f:
        data = json.load(f)
        resp = requests.post('http://' + ip + ':8080/v2/apps', json=data)


def start_services():
    instances = get_instances(is_slave=False)
    ip = get_public_ip(instances[0])
    master_ips = [get_private_ip(instance) for instance in get_instances(is_slave=False)]
    services = []

#    prepare_cassandra(master_ips)

    #List all services here
    start_service(ip, "app_config_files/zookeeper.json")
    time.sleep(10)
    start_service(ip, "app_config_files/kafka.json")
    time.sleep(10)
    start_service(ip, "app_config_files/cassandra.json")
    time.sleep(20)
    start_service(ip, "app_config_files/spark_temperature.json")
    start_service(ip, "app_config_files/spark_tweet.json")
    time.sleep(30)
    start_service(ip, "app_config_files/akka_weatherfinder.json")
    start_service(ip, "app_config_files/akka_tweet.json")
        

if __name__ == "__main__":
    start_services()
