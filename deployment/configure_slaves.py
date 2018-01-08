import subprocess as sp

import config as cfg
from get_instances import get_instances, get_private_ip, get_public_ip, is_master
from tools import ssh


def init_slave(ip):
    ssh(ip, 'sudo apt-key adv --keyserver keyserver.ubuntu.com --recv E56151BF')
    ssh(ip, 'echo "deb http://repos.mesosphere.io/' + cfg.MESOS_REPO_DISTRIB + ' ' + cfg.MESOS_REPO_CODENAME + ' main" | sudo tee /etc/apt/sources.list.d/mesosphere.list')
    ssh(ip, 'sudo apt-get -y update')
    ssh(ip, 'sudo apt-get -y install mesos')


def configure_zookeeper(pub_ip, master_ips):
    cmd = 'echo zk://'
    for ip in master_ips:
        cmd += ip + ':2181,'
    cmd = cmd[:-1] + '/mesos | sudo tee /etc/mesos/zk'
    cmd += 'sudo service zookeeper stop'
    cmd += 'echo manual | sudo tee /etc/init/zookeeper.override'
    print(cmd)


def configure_mesos(pub_ip, priv_ip):
    cmd = 'sudo service mesos-master stop'
    cmd += 'echo manual | sudo tee /etc/init/mesos-master.override'
    cmd += 'echo ' + priv_ip + ' | sudo tee /etc/mesos-slave/ip'
    cmd += 'sudo cp /etc/mesos-slave/ip /etc/mesos-slave/hostname'
    cmd += 'echo "cgroups/cpu,cgroups/mem" | sudo tee /etc/mesos-slave/isolation'
    print(cmd)


def start_slave(pub_ip):
    ssh(pub_ip, 'sudo service mesos-slave start')


if __name__ == "__main__":
    master_ips = [get_private_ip(instance) for instance in get_instances() if is_master(instance)]

    for instance in get_instances():
        if is_master(instance):
            continue

        priv_ip = get_private_ip(instance)
        pub_ip = get_public_ip(instance)
        init_slave(pub_ip)
        #configure_zookeeper(pub_ip, master_ips)
        #configure_mesos(pub_ip, priv_ip)
        #start_slave(pub_ip)
