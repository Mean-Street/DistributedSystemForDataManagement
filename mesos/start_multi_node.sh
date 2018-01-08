#! /bin/sh

#REQUIRES: 3 Masters and 2+ Slaves

#This script starts Mesos-Master, ZooKeeper and Marathon on the 3 masters in separate containers, and Mesos-Slave on the 2 slaves
#The slaves aren't started in containers since it would create docker containers inside another container when executing tasks
#Additional slaves can be added by using the same code than the first 2 and just changing the ip address

#This script needs the addresses of the masters. The address af slaves can be given each time a new slave is created in order to simplify scaling

#TODO: automatize the creation of instances and automatically get all ip addresses instead of having them written in the file
#TODO: factorize the functions by using parameters (if possible with ssh)

#ISSUES: maybe Zookeeper needs to be started on all masters before starting other frameworks?


M1_PUBLIC_IP=35.182.98.222
M2_PUBLIC_IP=52.60.220.189
M3_PUBLIC_IP=35.182.31.68
S1_PUBLIC_IP=35.182.194.54
S2_PUBLIC_IP=35.182.16.192

M1_PRIVATE_IP=10.0.0.212
M2_PRIVATE_IP=10.0.0.233
M3_PRIVATE_IP=10.0.0.192
S1_PRIVATE_IP=10.0.0.228
S2_PRIVATE_IP=10.0.0.37


start_master_1()
{
    M1_PRIVATE_IP=10.0.0.212
    M2_PRIVATE_IP=10.0.0.233
    M3_PRIVATE_IP=10.0.0.192
    S1_PRIVATE_IP=10.0.0.228
    S2_PRIVATE_IP=10.0.0.37

    echo "Installing Docker"
    sudo apt-get install -y docker.io

    echo "Starting ZooKeeper"
    sudo docker run -d \
      --net="host" \
      -e SERVER_ID=1 \
      -e ADDITIONAL_ZOOKEEPER_1=server.1=${M1_PRIVATE_IP}:2888:3888 \
      -e ADDITIONAL_ZOOKEEPER_2=server.2=${M2_PRIVATE_IP}:2888:3888 \
      -e ADDITIONAL_ZOOKEEPER_3=server.3=${M3_PRIVATE_IP}:2888:3888 \
      mesoscloud/zookeeper

    echo "Starting Mesos-Master"
    sudo docker run --net="host" \
       -p 5050:5050 \
       -e "MESOS_HOSTNAME=${M1_PRIVATE_IP}" \
       -e "MESOS_IP=${M1_PRIVATE_IP}" \
       -e "MESOS_ZK=zk://${M1_PRIVATE_IP}:2181,${M2_PRIVATE_IP}:2181,${M3_PRIVATE_IP}:2181/mesos" \
       -e "MESOS_PORT=5050" \
       -e "MESOS_LOG_DIR=/var/log/mesos" \
       -e "MESOS_QUORUM=2" \
       -e "MESOS_REGISTRY=in_memory" \
       -e "MESOS_WORK_DIR=/var/lib/mesos" \
       -d \
       mesosphere/mesos-master:1.3.2-rc1

    echo "Starting Marathon"
    sudo docker run \
      -d \
      -p 8080:8080 \
      mesosphere/marathon:v1.4.9 --master zk://${M1_PRIVATE_IP}:2181,${M2_PRIVATE_IP}:2181,${M3_PRIVATE_IP}:2181/mesos --zk zk://${M1_PRIVATE_IP}:2181,${M2_PRIVATE_IP}:2181,${M3_PRIVATE_IP}:2181/marathon

    echo "Closing Connection"
}

start_master_2()
{
    M1_PRIVATE_IP=10.0.0.212
    M2_PRIVATE_IP=10.0.0.233
    M3_PRIVATE_IP=10.0.0.192
    S1_PRIVATE_IP=10.0.0.228
    S2_PRIVATE_IP=10.0.0.37

    echo "Installing Docker"
    sudo apt-get install -y docker.io

    echo "Starting ZooKeeper"
    sudo docker run -d \
      --net="host" \
      -e SERVER_ID=2 \
      -e ADDITIONAL_ZOOKEEPER_1=server.1=${M1_PRIVATE_IP}:2888:3888 \
      -e ADDITIONAL_ZOOKEEPER_2=server.2=${M2_PRIVATE_IP}:2888:3888 \
      -e ADDITIONAL_ZOOKEEPER_3=server.3=${M3_PRIVATE_IP}:2888:3888 \
      garland/zookeeper

    echo "Starting Mesos-Master"
    sudo docker run --net="host" \
       -p 5050:5050 \
       -e "MESOS_HOSTNAME=${M2_PRIVATE_IP}" \
       -e "MESOS_IP=${M2_PRIVATE_IP}" \
       -e "MESOS_ZK=zk://${M1_PRIVATE_IP}:2181,${M2_PRIVATE_IP}:2181,${M3_PRIVATE_IP}:2181/mesos" \
       -e "MESOS_PORT=5050" \
       -e "MESOS_LOG_DIR=/var/log/mesos" \
       -e "MESOS_QUORUM=2" \
       -e "MESOS_REGISTRY=in_memory" \
       -e "MESOS_WORK_DIR=/var/lib/mesos" \
       -d \
       mesosphere/mesos-master:1.3.2-rc1

    echo "Starting Marathon"
    sudo docker run \
      -d \
      --net="host" \
      -p 8080:8080 \
      mesosphere/marathon:v1.4.9 --master zk://${M1_PRIVATE_IP}:2181,${M2_PRIVATE_IP}:2181,${M3_PRIVATE_IP}:2181/mesos --zk zk://${M1_PRIVATE_IP}:2181,${M2_PRIVATE_IP}:2181,${M3_PRIVATE_IP}:2181/marathon

    echo "Closing Connection"
}

start_master_3()
{
    M1_PRIVATE_IP=10.0.0.212
    M2_PRIVATE_IP=10.0.0.233
    M3_PRIVATE_IP=10.0.0.192
    S1_PRIVATE_IP=10.0.0.228
    S2_PRIVATE_IP=10.0.0.37

    echo "Installing Docker"
    sudo apt-get install -y docker.io

    echo "Starting ZooKeeper"
    sudo docker run -d \
      --net="host" \
      -e SERVER_ID=3 \
      -e ADDITIONAL_ZOOKEEPER_1=server.1=${M1_PRIVATE_IP}:2888:3888 \
      -e ADDITIONAL_ZOOKEEPER_2=server.2=${M2_PRIVATE_IP}:2888:3888 \
      -e ADDITIONAL_ZOOKEEPER_3=server.3=${M3_PRIVATE_IP}:2888:3888 \
      garland/zookeeper

    echo "Starting Mesos-Master"
    sudo docker run --net="host" \
       -p 5050:5050 \
       -e "MESOS_HOSTNAME=${M3_PRIVATE_IP}" \
       -e "MESOS_IP=${M3_PRIVATE_IP}" \
       -e "MESOS_ZK=zk://${M1_PRIVATE_IP}:2181,${M2_PRIVATE_IP}:2181,${M3_PRIVATE_IP}:2181/mesos" \
       -e "MESOS_PORT=5050" \
       -e "MESOS_LOG_DIR=/var/log/mesos" \
       -e "MESOS_QUORUM=2" \
       -e "MESOS_REGISTRY=in_memory" \
       -e "MESOS_WORK_DIR=/var/lib/mesos" \
       -d \
       mesosphere/mesos-master:1.3.2-rc1

    echo "Starting Marathon"
    sudo docker run \
      -d \
      -p 8080:8080 \
      mesosphere/marathon:v1.4.9 --master zk://${M1_PRIVATE_IP}:2181,${M2_PRIVATE_IP}:2181,${M3_PRIVATE_IP}:2181/mesos --zk zk://${M1_PRIVATE_IP}:2181,${M2_PRIVATE_IP}:2181,${M3_PRIVATE_IP}:2181/marathon

    echo "Closing Connection"
}


start_slave_1()
{
    M1_PRIVATE_IP=10.0.0.212
    M2_PRIVATE_IP=10.0.0.233
    M3_PRIVATE_IP=10.0.0.192
    S1_PRIVATE_IP=10.0.0.228
    S2_PRIVATE_IP=10.0.0.37

    
    echo "Installing Mesosphere"
    #Import the Mesosphere Archive Automatic Signing Key
    sudo apt-key adv --keyserver keyserver.ubuntu.com --recv E56151BF
    #Add the Mesosphere Ubuntu 14.04 Repo
    DISTRO=$(lsb_release -is | tr '[:upper:]' '[:lower:]')
    CODENAME=$(lsb_release -cs)
    echo "deb http://repos.mesosphere.io/${DISTRO} ${CODENAME} main" | \
    sudo tee /etc/apt/sources.list.d/mesosphere.list
    #Download package lists and information of latest versions
    sudo apt-get -y update
    #Install Mesos and Zookeper
    sudo apt-get -y install mesos

    echo "Configuring Zookeeper"
    #Specify the master adresses to Zookeeper
    echo zk://${M1_PRIVATE_IP}:2181,${M2_PRIVATE_IP}:2181,${M3_PRIVATE_IP}:2181/mesos | sudo tee /etc/mesos/zk
    #Stop and disable the Zookeeper service
    sudo service zookeeper stop
    echo manual | sudo tee /etc/init/zookeeper.override

    echo "Configuring Mesos-slave"
    #Stop and disable the Mesos Master service
    sudo service mesos-master stop
    echo manual | sudo tee /etc/init/mesos-master.override
    #Set Slave ip
    echo ${S1_PRIVATE_IP} | sudo tee /etc/mesos-slave/ip
    sudo cp /etc/mesos-slave/ip /etc/mesos-slave/hostname
    echo "cgroups/cpu,cgroups/mem" | sudo tee /etc/mesos-slave/isolation

    echo "Starting Slave"
    #Start the mesos-slave service
    sudo service mesos-slave start

    echo "Closing Connection"
}

start_slave_2()
{
    M1_PRIVATE_IP=10.0.0.212
    M2_PRIVATE_IP=10.0.0.233
    M3_PRIVATE_IP=10.0.0.192
    S1_PRIVATE_IP=10.0.0.228
    S2_PRIVATE_IP=10.0.0.37

    
    echo "Installing Mesosphere"
    #Import the Mesosphere Archive Automatic Signing Key
    sudo apt-key adv --keyserver keyserver.ubuntu.com --recv E56151BF
    #Add the Mesosphere Ubuntu 14.04 Repo
    DISTRO=$(lsb_release -is | tr '[:upper:]' '[:lower:]')
    CODENAME=$(lsb_release -cs)
    echo "deb http://repos.mesosphere.io/${DISTRO} ${CODENAME} main" | \
    sudo tee /etc/apt/sources.list.d/mesosphere.list
    #Download package lists and information of latest versions
    sudo apt-get -y update
    #Install Mesos and Zookeper
    sudo apt-get -y install mesos

    echo "Configuring Zookeeper"
    #Specify the master adresses to Zookeeper
    echo zk://${M1_PRIVATE_IP}:2181,${M2_PRIVATE_IP}:2181,${M3_PRIVATE_IP}:2181/mesos | sudo tee /etc/mesos/zk
    #Stop and disable the Zookeeper service
    sudo service zookeeper stop
    echo manual | sudo tee /etc/init/zookeeper.override

    echo "Configuring Mesos-slave"
    #Stop and disable the Mesos Master service
    sudo service mesos-master stop
    echo manual | sudo tee /etc/init/mesos-master.override
    #Set Slave ip
    echo ${S2_PRIVATE_IP} | sudo tee /etc/mesos-slave/ip
    sudo cp /etc/mesos-slave/ip /etc/mesos-slave/hostname
    echo "cgroups/cpu,cgroups/mem" | sudo tee /etc/mesos-slave/isolation

    echo "Starting Slave"
    #Start the mesos-slave service
    sudo service mesos-slave start

    echo "Closing Connection"
}

#Configuring Masters
echo "#############################"
echo "###   STARTING MASTERS   ####"
echo "#############################"

echo "Connecting to Master 1 @ ${M1_PUBLIC_IP}"
ssh -i "smack.pem" -o StrictHostKeyChecking=no ubuntu@${M1_PUBLIC_IP} "$(typeset -f); start_master_1"

echo "Connecting to Master 2 @ ${M2_PUBLIC_IP}"
ssh -i "smack.pem" -o StrictHostKeyChecking=no ubuntu@${M2_PUBLIC_IP} "$(typeset -f); start_master_2"

echo "Connecting to Master 3 @ ${M3_PUBLIC_IP}"
ssh -i "smack.pem" -o StrictHostKeyChecking=no ubuntu@${M3_PUBLIC_IP} "$(typeset -f); start_master_3"


#Configuring and starting Slaves
echo "############################"
echo "###   STARTING SLAVES   ####"
echo "############################"

echo "Connecting to Slave 1 @ ${S1_PUBLIC_IP}"
ssh -i "smack.pem" -o StrictHostKeyChecking=no ubuntu@${S1_PUBLIC_IP} "$(typeset -f); start_slave_1"

echo "Connecting to Slave 2 @ ${S2_PUBLIC_IP}"
ssh -i "smack.pem" -o StrictHostKeyChecking=no ubuntu@${S2_PUBLIC_IP} "$(typeset -f); start_slave_2"
