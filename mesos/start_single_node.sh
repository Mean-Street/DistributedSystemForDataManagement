#! /bin/sh

#This script will create a Mesos cluster on a single instance
#The master and slave will both run on that instance
#Zookeeper and Marathon will also be installed and started


#Public IP of the instance
PUBLIC_IP=35.182.16.192

start_solo_node()
{
    #Private IP of the instance
    PRIVATE_IP=10.0.0.38

    #Install Zookeeper
    sudo apt-get -y update
    sudo apt-get -y install zookeeperd

    #Install Mesos
    sudo apt-key adv --keyserver keyserver.ubuntu.com --recv E56151BF
    DISTRO=$(lsb_release -is | tr '[:upper:]' '[:lower:]')
    CODENAME=$(lsb_release -cs)
    echo "deb http://repos.mesosphere.com/${DISTRO} ${CODENAME} main" | sudo tee /etc/apt/sources.list.d/mesosphere.list
    sudo apt-get -y update
    sudo apt-get -y install mesos

    #Start Mesos master and slave
    echo ${PRIVATE_IP} | sudo tee /etc/mesos-master/advertise_ip
    echo ${PRIVATE_IP} | sudo tee /etc/mesos-slave/advertise_ip
    sudo service mesos-master start
    sudo service mesos-slave start

    #Install Marathon
}

echo "Connecting to Instance @ ${PUBLIC_IP}"
ssh -i "smack.pem" -o StrictHostKeyChecking=no ubuntu@${PUBLIC_IP} "$(typeset -f); start_solo_node"





