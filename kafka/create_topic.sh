#!/bin/bash

zooaddress=$1
zooport=$2
topic=$3

git pull kafka-docker/
kafka-docker/create-topics.sh --create --zookeeper $zooaddress:$zooport --replication-factor 1 --partitions 10 --topic $topic
