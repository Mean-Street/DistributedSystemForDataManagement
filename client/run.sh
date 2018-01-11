#!/bin/bash

if [[ -z "$3" ]]
then
    echo "Usage: ./run.sh cassandra_host cassandra_port scala_class [class-args]"
    echo "Example: ./run.sh localhost 9042 Test arg0"
    exit 1
fi

cassandra_host=$1
shift
cassandra_port=$1
shift

docker run --rm -it -p 4040:4040 -p 8080:8080 -e CASSANDRA_HOST="$cassandra_host" -e CASSANDRA_PORT="$cassandra_port" spark-client run-spark $@
