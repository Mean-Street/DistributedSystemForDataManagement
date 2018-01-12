import os
import subprocess as sp

import config as cfg
from get_instances import get_instances, get_private_ip, get_public_ip
from tools import ssh


# The following works in a shell:
# ssh -i /home/vincent/Documents/ensimag/cours/3a/smack/DistributedSystemForDataManagement/smack.pem -o StrictHostKeyChecking=no ubuntu@52.47.104.11 'bash -s' < init_slave.sh
def init_slave(ip):
    return ssh(ip, "'bash -s' < init_slave.sh", shell=True)


def start_marathonlb(pub_ip, master_ips):
    cmd = 'sudo docker run -d --net=host -e PORTS=9090 mesosphere/marathon-lb sse --group=* -m'
    for ip in master_ips:
        cmd +=  ' http://' + ip + ':8080'
    return ssh(pub_ip, cmd)


def generate_zookeeper_script(fname, master_ips):
    cmd = 'echo zk://'
    for ip in master_ips:
        cmd += ip + ':2181,'
    cmd = cmd[:-1] + '/mesos | sudo tee /etc/mesos/zk > /dev/null'
    cmd += '\nsudo service zookeeper stop'
    cmd += '\necho manual | sudo tee /etc/init/zookeeper.override > /dev/null'
    with open(fname, 'w') as f:
        f.write(cmd)


def configure_zookeeper(pub_ip, script_path):
    return ssh(pub_ip, "'bash -s' < " + script_path, shell=True)


def generate_mesos_script(fname, priv_ip):
    cmd = 'sudo service mesos-master stop'
    cmd += '\necho manual | sudo tee /etc/init/mesos-master.override > /dev/null'
    cmd += '\necho ' + priv_ip + ' | sudo tee /etc/mesos-slave/ip > /dev/null'
    cmd += '\nsudo cp /etc/mesos-slave/ip /etc/mesos-slave/hostname'
    cmd += '\necho "cgroups/cpu,cgroups/mem" | sudo tee /etc/mesos-slave/isolation > /dev/null'
    cmd += '\necho "docker,mesos" | sudo tee /etc/mesos-slave/containerizers'
    cmd += '\necho "10mins" | sudo tee /etc/mesos-slave/executor_registration_timeout'
    with open(fname, 'w') as f:
        f.write(cmd)


def configure_mesos(pub_ip, script_path):
    return ssh(pub_ip, "'bash -s' < " + script_path, shell=True)


def start_slave(pub_ip):
    return ssh(pub_ip, 'sudo service mesos-slave restart')


def print_header(text):
    print("\n")
    print("-------------------")
    print(text)
    print("-------------------")
    print("\n")


def configure_slave(instance):
    master_ips = [get_private_ip(inst) for inst in get_instances(is_slave=False)]

    ZOOKEEPER_PATH = 'slave_zookeeper.sh'
    MESOS_PATH = 'slave_mesos.sh'
    generate_zookeeper_script(ZOOKEEPER_PATH, master_ips)
    generate_mesos_script(MESOS_PATH, get_private_ip(instance))
    
    p = init_slave(get_public_ip(instance))
    p.wait()
    p = configure_zookeeper(get_public_ip(instance), ZOOKEEPER_PATH)
    p.wait()
    p = configure_mesos(get_public_ip(instance), MESOS_PATH)
    p.wait()
    p = start_slave(get_public_ip(instance))
    p.wait()
    p = start_marathonlb(get_public_ip(instance), master_ips)
    p.wait()


def configure_slaves():
    master_ips = [get_private_ip(instance) for instance in get_instances(is_slave=False)]

    ZOOKEEPER_PATH = 'slave_zookeeper.sh'
    MESOS_PATH = 'slave_mesos.sh'
    generate_zookeeper_script(ZOOKEEPER_PATH, master_ips)

    print_header("Installing packages...")
    processes = []
    for instance in get_instances(is_slave=True):
        processes.append(init_slave(get_public_ip(instance)))
    for p in processes:
        p.wait()

    print_header("Configuring Zookeeper...")
    processes = []
    for instance in get_instances(is_slave=True):
        processes.append(configure_zookeeper(get_public_ip(instance), ZOOKEEPER_PATH))
    for p in processes:
        p.wait()

    print_header("Starting Mesos...")
    for instance in get_instances(is_slave=True):
        generate_mesos_script(MESOS_PATH, get_private_ip(instance))
        # Cannot parallelize because we would override the file in which the script
        # is written
        p = configure_mesos(get_public_ip(instance), MESOS_PATH)
        p.wait()
        start_slave(get_public_ip(instance))

    os.remove(ZOOKEEPER_PATH)
    os.remove(MESOS_PATH)

    print_header("Starting MarathonLB")
    processes = []
    for instance in get_instances(is_slave=True):
        processes.append(start_marathonlb(get_public_ip(instance), master_ips))
    for p in processes:
        p.wait()


if __name__ == "__main__":
    configure_slaves()
