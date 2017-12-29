#! /bin/sh

#This script will create a Mesos cluster on two nodes : a master and a slave
#Zookeeper and Marathon will run on the Master


#Public IPs of the instances
M_PUBLIC_IP=35.182.16.192
S_PUBLIC_IP=35.182.194.54

start_solo_master()
{
    #Master privite IP
    M_PRIVATE_IP=10.0.0.169

    echo "Installing Zookeeper"
    sudo apt-get -y update
    sudo apt-get -y install zookeeperd

    echo "Configuring Zookeeper"
    #Specify Master Id
    echo 1 | sudo tee /etc/zookeeper/conf/myid
    sudo cp sudo tee /etc/zookeeper/conf/myid /var/lib/zookeeper/myid
    #Specify mapping
    echo tickTime=2000 | sudo tee /etc/zookeeper/conf/zoo.cfg
    echo dataDir=/var/lib/zookeeper/ | sudo tee -a /etc/zookeeper/conf/zoo.cfg
    echo clientPort=2181 | sudo tee -a /etc/zookeeper/conf/zoo.cfg
    echo initLimit=10 | sudo tee -a /etc/zookeeper/conf/zoo.cfg
    echo syncLimit=5 | sudo tee -a /etc/zookeeper/conf/zoo.cfg
    echo server.1=${M_PRIVATE_IP}:2888:3888 | sudo tee -a /etc/zookeeper/conf/zoo.cfg
    sudo service zookepeer start


    echo "Installing Mesos"
    sudo apt-key adv --keyserver keyserver.ubuntu.com --recv E56151BF
    DISTRO=$(lsb_release -is | tr '[:upper:]' '[:lower:]')
    CODENAME=$(lsb_release -cs)
    echo "deb http://repos.mesosphere.com/${DISTRO} ${CODENAME} main" | sudo tee /etc/apt/sources.list.d/mesosphere.list
    sudo apt-get -y update
    sudo apt-get -y install mesos

    echo "Configuring Mesos-master"
    #Specify a human readable name for the cluster
    echo SDTDCluster | sudo tee /etc/mesos-master/cluster
    #Point Master to Zookeeper
    echo zk://${M_PRIVATE_IP}:2181/mesos | sudo tee /etc/mesos/zk
    #Specify quorum (> nMaster/2)
    echo 1 | sudo tee /etc/mesos-master/quorum
    #Set Master ip
    echo ${M_PRIVATE_IP} | sudo tee /etc/mesos-master/ip
    sudo cp /etc/mesos-master/ip /etc/mesos-master/hostname
    #Start Mesos master
    sudo service mesos-master restart


    echo "Installing Marathon"
    sudo apt-get -y install marathon

    echo "Configuring Marathon"
    #Set hostname
    sudo mkdir -p /etc/marathon/conf
    sudo cp /etc/mesos-master/hostname /etc/marathon/conf/hostname
    #Start Marathon
    sudo service marathon restart

    echo "Closing Connection"
}


start_solo_slave()
{
    #Master privite IP
    M_PRIVATE_IP=10.0.0.169
    #Slave Private IP
    S_PRIVATE_IP=10.0.0.201

    echo "Installing Mesos"
    sudo apt-key adv --keyserver keyserver.ubuntu.com --recv E56151BF
    DISTRO=$(lsb_release -is | tr '[:upper:]' '[:lower:]')
    CODENAME=$(lsb_release -cs)
    echo "deb http://repos.mesosphere.com/${DISTRO} ${CODENAME} main" | sudo tee /etc/apt/sources.list.d/mesosphere.list
    sudo apt-get -y update
    sudo apt-get -y install mesos

    echo "Configuring Mesos-slave"
    #Stop and disable the Mesos Master service
    sudo service mesos-master stop
    echo manual | sudo tee /etc/init/mesos-master.override
    #Prepare Docker environment
    echo "docker,mesos" | sudo tee /etc/mesos-slave/containerizers
    echo "15mins" | sudo tee /etc/mesos-slave/executor_registration_timeout
    #Point Slave to Zookeeper
    echo zk://${M_PRIVATE_IP}:2181/mesos | sudo tee /etc/mesos/zk
    #Set Slave ip
    echo ${S_PRIVATE_IP} | sudo tee /etc/mesos-slave/ip
    sudo cp /etc/mesos-slave/ip /etc/mesos-slave/hostname
    #Start the mesos-slave service
    sudo service mesos-slave restart

    echo "Closing Connection"
}

echo "Connecting to Master @ ${M_PUBLIC_IP}"
ssh -i "smack.pem" -o StrictHostKeyChecking=no ubuntu@${M_PUBLIC_IP} "$(typeset -f); start_solo_master"

echo "Connecting to Slave @ ${S_PUBLIC_IP}"
ssh -i "smack.pem" -o StrictHostKeyChecking=no ubuntu@${S_PUBLIC_IP} "$(typeset -f); start_solo_slave"

