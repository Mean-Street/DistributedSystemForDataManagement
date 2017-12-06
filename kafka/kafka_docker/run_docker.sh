#!/bin/bash

#zookeeper_address=192.168.1.72
#zookeeper_port=2182

#docker pull wurstmeister/kafka
#dockerd
#docker-compose -f docker-compose-single-broker.yml up
#./kafka-topics.sh --create --zookeeper $zookeeper_address:$zookeeper_port --replication-factor 1 --partitions 10 --topic meteodata
dockerd
docker pull zookeeper
git clone https://github.com/wurstmeister/kafka-docker build kafka-docker/ -t kafka_docker
docker run --name smack-zookeeper -p 2181:2181 -d zookeeper
