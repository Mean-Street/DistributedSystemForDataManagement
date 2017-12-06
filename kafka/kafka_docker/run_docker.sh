#!/bin/bash

zookeeper_address=192.168.1.72
zookeeper_port=2182

docker pull wurstmeister/kafka
dockerd
docker-compose -f docker-compose-single-broker.yml up
./kafka-topics.sh --create --zookeeper $zookeeper_address:$zookeeper_port --replication-factor 1 --partitions 10 --topic meteodata
