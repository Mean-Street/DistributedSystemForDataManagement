#!/bin/sh

# Launch Zookeep (TODO: from Docker)
# bin/zookeeper-server-start.sh config/zookeeper.properties
# Launch Kafka server (TODO: from Docker)
# bin/kafka-server-start.sh config/server.properties

if [[ -z "$1" ]]
then
    echo "Usage: ./run_data_preprocessing.sh zookeeper-host:zookeeper-port"
    echo "Example: ./run_data_preprocessing.sh 192.168.1.33:2181"
    exit 1
fi

./run_docker.sh DataPreprocessing "$1" group1 test 1 2
