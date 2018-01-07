#!/bin/bash

kafkahost=$1
kafkaport=$2
zookeeperhost=$3
zookeeperport=$4
topics=$5

docker run --name smack-kafka -it --rm -e KAFKA_ZOOKEEPER_CONNECT=$zookeeperhost:$zookeeperport -e KAFKA_ADVERTISED_HOST_NAME=$kafkahost -e KAFKA_ADVERTISED_PORT=$kafkaport -e KAFKA_CREATE_TOPICS="$topics" -p $kafkaport:9092 sdtdensimag/kafka
