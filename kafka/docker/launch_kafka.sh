#!/bin/bash

echo "" >> /opt/kafka/config/server.properties
echo "zookeeper.connect=$KAFKA_ZOOKEEPER_CONNECT" >> /opt/kafka/config/server.properties
echo "advertised.listeners=PLAINTEXT://$KAFKA_ADVERTISED_HOST_NAME:$KAFKA_ADVERTISED_PORT" >> /opt/kafka/config/server.properties
start-kafka.sh
