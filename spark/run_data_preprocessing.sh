#!/bin/bash

if [[ -z "$1" ]]
then
    echo "Usage: ./run_data_preprocessing.sh kafka-host:kafka-port topic"
    echo "Example: ./run_data_preprocessing.sh 192.168.1.33:9092 test"
    exit 1
fi

# DataPreprocessing <brokers> <consumer-group> <topics> <numThreads> <pulling-period>
./run_docker.sh DataPreprocessing "$1" group "$2" 1 2
