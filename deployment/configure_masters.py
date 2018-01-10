import config as cfg
from get_instances import get_instances, get_private_ip, get_public_ip, is_master
from tools import ssh


def init_master(ip):
    return ssh(ip, "sudo apt-get update; sudo apt-get install -y docker.io")


def start_zookeeper(pub_ip, id_, master_ips):
    cmd = 'sudo docker run -d --net="host" -e SERVER_ID=' + str(id_)
    for i, ip in enumerate(master_ips):
        index = str(i+1)
        current = ip
        if id_ == i+1:
            current = "0.0.0.0"
        cmd += ' -e ADDITIONAL_ZOOKEEPER_' + index + '=server.' + index + '=' + current + ':2888:3888'
    cmd += ' mesoscloud/zookeeper'
    return ssh(pub_ip, cmd)


def start_mesos(pub_ip, priv_ip, master_ips):
    cmd = ('sudo docker run --net="host" -p 5050:5050 -e "MESOS_HOSTNAME=' + priv_ip +
           '" -e "MESOS_IP=' + priv_ip +'" -e "MESOS_ZK=zk://')
    for ip in master_ips:
        cmd += ip + ':2181,'
    cmd = cmd[:-1]
    cmd += '/mesos" -e "MESOS_PORT=5050" -e "MESOS_LOG_DIR=/var/log/mesos" -e "MESOS_QUORUM=2"'
    cmd += ' -e "MESOS_REGISTRY=in_memory" -e "MESOS_WORK_DIR=/var/lib/mesos" -d'
    cmd += ' mesosphere/mesos-master:1.3.2-rc1'
    return ssh(pub_ip, cmd)


def start_marathon(pub_ip, master_ips):
    cmd = 'sudo docker run -d -p 8080:8080 mesosphere/marathon:v1.4.9 --master zk://'
    zk = ''
    for ip in master_ips:
        zk += ip + ':2181,'
    zk = zk[:-1]
    cmd += zk + '/mesos --zk zk://'
    cmd += zk + '/marathon'
    return ssh(pub_ip, cmd)


def print_header(text):
    print("\n\n")
    print("-------------------")
    print(text)
    print("-------------------")
    print("\n\n")


if __name__ == "__main__":
    private_ips = [get_private_ip(instance) for instance in get_instances(is_slave=False)]

    print_header("Installing packages...")
    processes = []
    for instance in get_instances(is_slave=False):
        processes.append(init_master(get_public_ip(instance)))
    for p in processes:
        p.wait()

    print_header("Starting Zookeeper...")
    processes = []
    for i, instance in enumerate(get_instances(is_slave=False)):
        processes.append(start_zookeeper(get_public_ip(instance), i+1, private_ips))
    for p in processes:
        p.wait()

    print_header("Starting Mesos...")
    processes = []
    for instance in get_instances(is_slave=False):
        processes.append(start_mesos(get_public_ip(instance), get_private_ip(instance), private_ips))
    for p in processes:
        p.wait()

    print_header("Starting Marathon...")
    processes = []
    for instance in get_instances(is_slave=False):
        processes.append(start_marathon(get_public_ip(instance), private_ips))
    for p in processes:
        p.wait()
