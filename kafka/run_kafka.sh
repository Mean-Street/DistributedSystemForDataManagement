#!/bin/bash

kafkahost=$1
kafkaport=$2
zookeeperhost=$3
zookeeperport=$4
topics=$5

rm -f kafka-docker
git clone https://github.com/wurstmeister/kafka-docker 
docker build kafka-docker/ -t kafka_docker
docker run --name smack-kafka -it --rm -e KAFKA_ZOOKEEPER_CONNECT=$zookeeperhost:$zookeeperport -e KAFKA_ADVERTISED_HOST_NAME=$kafkahost -e KAFKA_CREATE_TOPICS="$topics" -p $kafkaport:9092 -v $(pwd)/server.properties:/opt/kafka/config/server.properties kafka_docker
