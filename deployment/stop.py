from terminate_instances import terminate_instances
from get_instances import get_instances, get_public_ip, is_master
from tools import ssh


def stop_master(ip):
    p = ssh(ip, "sudo docker stop $(sudo docker ps -a -q)")
    p.wait()
    print("Master @" + ip + " stopped")


def stop_slave(ip):
    p = ssh(ip, "sudo docker stop $(sudo docker ps -a -q)")
    p.wait()
    print("Slave @" + ip + " stopped")


def print_header(text):
    print("\n")
    print("-------------------")
    print(text)
    print("-------------------")
    print("\n")


if __name__ == "__main__":
    print_header("Stoping Masters...")
    for instance in get_instances(is_slave=False):
        stop_master(get_public_ip(instance))


    print_header("Stoping Slaves...")
    for instance in get_instances(is_slave=True):
        stop_slave(get_public_ip(instance))


    print_header("Shutting Down...")
    terminate_instances()
