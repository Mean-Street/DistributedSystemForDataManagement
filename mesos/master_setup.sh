#! /bin/sh

MASTER_IP=10.0.0.100

#Install mesosphere
sudo apt-key adv --keyserver keyserver.ubuntu.com --recv E56151BF
DISTRO=$(lsb_release -is | tr '[:upper:]' '[:lower:]')
CODENAME=$(lsb_release -cs)
echo "deb http://repos.mesosphere.io/${DISTRO} ${CODENAME} main" | \
sudo tee /etc/apt/sources.list.d/mesosphere.list
sudo apt-get -y update
sudo apt-get -y install mesos marathon

#Configure master
sudo service mesos-slave stop
echo manual | sudo tee /etc/init/mesos-slave.override
echo ${MASTER_IP} | sudo tee /etc/mesos-master/ip
echo zk://${MASTER_IP}:2181/mesos | sudo tee /etc/mesos/zk
echo SDTDCluster | sudo tee /etc/mesos-master/cluster
echo ${MASTER_IP} | sudo tee /etc/mesos-master/hostname

#Configure zookeeper
echo 1 | sudo tee /etc/mesos-master/quorum
echo 1 | sudo tee /etc/zookeeper/conf/myid

#Start
sudo service zookeeper restart
sudo service mesos-master restart
MASTER_IP=10.0.0.100
marathon --master zk://${MASTER_IP}:2181/mesos --zk zk://${MASTER_IP}:2181/marathon
#marathon --master zk://10.0.0.100:2181/mesos --zk zk://10.0.0.100:2181/marathon

#/lib/systemd/system/marathon.service
# [Unit]
# Description=Scheduler for Apache Mesos
# Requires=network.target
#
# [Service]
# Type=simple
# WorkingDirectory=/usr/share/marathon
# EnvironmentFile=/etc/default/marathon
# ExecStart=/usr/share/marathon/bin/marathon --master zk://10.0.0.100:2181/mesos --zk zk://10.0.0.100:2181/marathon
# ExecReload=/bin/kill -HUP $MAINPID
# Restart=always
# RestartSec=60
# SuccessExitStatus=
# User=marathon
# ExecStartPre=/bin/mkdir -p /run/marathon
# ExecStartPre=/bin/chown marathon:marathon /run/marathon
# ExecStartPre=/bin/chmod 755 /run/marathon
# PermissionsStartOnly=true
# LimitNOFILE=1024
#
# [Install]
# WantedBy=multi-user.target


#Zookeeper
#sudo docker run -d --net=host netflixoss/exhibitor:1.5.2
#Mesos master
#sudo docker run -d --net=host \
#  -e MESOS_PORT=5050 \
#  -e MESOS_ZK=zk://${MASTER_IP}:2181/mesos \
#  -e MESOS_QUORUM=1 \
#  -e MESOS_REGISTRY=in_memory \
#  -e MESOS_LOG_DIR=/var/log/mesos \
#  -e MESOS_WORK_DIR=/var/tmp/mesos \
#  -v "$(pwd)/log/mesos:/var/log/mesos" \
#  -v "$(pwd)/tmp/mesos:/var/tmp/mesos" \
#  mesosphere/mesos-master:0.28.0-2.0.16.ubuntu1404

#sudo docker run -d --net=host \
#  -e MESOS_PORT=5050 \
#  -e MESOS_ZK=zk://10.0.0.100:2181/mesos \
#  -e MESOS_QUORUM=1 \
#  -e MESOS_REGISTRY=in_memory \
#  -e MESOS_LOG_DIR=/var/log/mesos \
#  -e MESOS_WORK_DIR=/var/tmp/mesos \
#  -v "$(pwd)/log/mesos:/var/log/mesos" \
#  -v "$(pwd)/tmp/mesos:/var/tmp/mesos" \
#  mesosphere/mesos-master:0.28.0-2.0.16.ubuntu1404

#sudo docker run -d --net=host  \
#  -p 8080:8080 \
#  mesosphere/marathon --master zk://10.0.0.100:2181/mesos --zk zk://10.0.0.100:2181/marathon
