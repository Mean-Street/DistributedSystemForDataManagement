#! /bin/sh

MASTER_IP=10.0.0.100

#Install docker
sudo apt-get install -y \
    apt-transport-https \
    ca-certificates \
    curl \
    software-properties-common
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
sudo add-apt-repository \
   "deb [arch=amd64] https://download.docker.com/linux/ubuntu \
   $(lsb_release -cs) \
   stable"
sudo apt-get update
sudo apt-get install -y docker-ce

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
echo ${MASTER_IP} | sudo tee /etc/mesos-master/MASTER_IP
echo zk://${MASTER_IP}:2181/mesos | sudo tee /etc/mesos/zk
echo SDTDCluster | sudo tee /etc/mesos-master/cluster
echo ${MASTER_IP} | sudo tee /etc/mesos-master/hostname

#Configure zookeeper
echo 1 | sudo tee /etc/mesos-master/quorum
echo 1 | sudo tee /etc/zookeeper/conf/myid

#Start
sudo service zookeeper restart
sudo service mesos-master restart
marathon --master zk://${MASTER_IP}:2181/mesos
#sudo service marathon restart
