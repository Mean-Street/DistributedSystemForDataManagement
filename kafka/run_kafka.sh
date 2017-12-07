#!/bin/sh

if [[ -z "$1" ]]
then
    echo "Usage: ./run_kafka.sh broker-port"
    echo "Example: ./run_kafka.sh 9092"
    exit 1
fi

docker build -t kafka_server .
docker run -it -p "$1":9092 -v $(pwd)/server.properties:/server.properties kafka_server
