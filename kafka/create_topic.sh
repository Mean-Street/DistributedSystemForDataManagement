#!/bin/bash

zooaddress=$1
zooport=$2
#zooaddress=10.0.2.15
#zooport=2181

git pull kafka-docker/
kafka-docker/create-topics.sh --create --zookeeper $zooaddress:$zooport --replication-factor 1 --partitions 10 --topic meteodata
