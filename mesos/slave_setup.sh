#! /bin/sh

SALVE_IP=10.0.0.200
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
sudo apt-get -y install mesos

#Configure slave
sudo service mesos-master stop
sudo service zookeeper stop
echo manual | sudo tee /etc/init/mesos-master.override
echo manual | sudo tee /etc/init/zookeeper.override
sudo apt-get -y remove --purge zookeeper
echo ${SALVE_IP} | sudo tee /etc/mesos-slave/ip
echo zk://${MASTER_IP}:2181/mesos | sudo tee /etc/mesos/zk
echo ${SALVE_IP} | sudo tee /etc/mesos-slave/hostname
echo "cgroups/cpu,cgroups/mem" | sudo tee /etc/mesos-slave/isolation

#Start
sudo service mesos-slave restart
