import config as cfg
from get_instances import get_instances, get_private_ip, get_public_ip, is_master
from tools import ssh


def init_master(ip):
    ssh(ip, "sudo apt-get update")
    ssh(ip, "sudo apt-get install -y docker.io")


def start_zookeeper(pub_ip, id_, master_ips):
    cmd = 'sudo docker run -d --net="host" -e SERVER_ID=' + str(id_)
    for i, ip in enumerate(master_ips):
        index = str(i+1) 
        cmd += ' -e ADDITIONAL_ZOOKEEPER_' + index + '=server.' + index + '=' + ip + ':2888:3888'
    cmd += ' mesoscloud/zookeeper'
    return ssh(pub_ip, cmd, False)


def start_mesos(pub_ip, priv_ip, master_ips):
    cmd = ('sudo docker run --net="host" -p 5050:5050 -e "MESOS_HOSTNAME=' + priv_ip +
           '" -e "MESOS_IP=' + priv_ip +'" -e "MESOS_ZK=zk://')
    for ip in master_ips:
        cmd += ip + ':2181,'
    cmd = cmd[:-1]
    cmd += '/mesos" -e "MESOS_PORT=5050" -e "MESOS_LOG_DIR=/var/log/mesos" -e "MESOS_QUORUM=2"'
    cmd += ' -e "MESOS_REGISTRY=in_memory" -e "MESOS_WORK_DIR=/var/lib/mesos" -d'
    cmd += ' mesosphere/mesos-master:1.3.2-rc1'
    return ssh(pub_ip, cmd, False)


def start_marathon(pub_ip, master_ips):
    cmd = 'sudo docker run -d -p 8080:8080 mesosphere/marathon:v1.4.9 --master zk://'
    zk = ''
    for ip in master_ips:
        zk += ip + ':2181,'
    zk = zk[:-1]
    cmd += zk + '/mesos --zk zk://'
    cmd += zk + '/marathon'
    return ssh(pub_ip, cmd, False)


if __name__ == "__main__":
    private_ips = [get_private_ip(instance) for instance in get_instances() if is_master(instance)]

    for i, instance in enumerate(get_instances()):
        if not is_master(instance):
            continue

        priv_ip = get_private_ip(instance)
        pub_ip = get_public_ip(instance)
        init_master(pub_ip)
        start_zookeeper(pub_ip, i+1, private_ips)
        start_mesos(pub_ip, priv_ip, private_ips)
        start_marathon(pub_ip, private_ips)
