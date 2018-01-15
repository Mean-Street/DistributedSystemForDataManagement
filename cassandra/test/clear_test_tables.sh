#!/bin/bash

if [[ -z "$1" ]]; then
    echo "Usage: ./clear_test_tables.sh <cassandra_docker_name>"
    echo "Example: ./clear_test_tables.sh cassandraContainer"
    exit 1
fi

DOCKER_NAME="$1"
CLEAR_TEST_TABLE="clear_test_tables.cql"
docker exec $DOCKER_NAME cqlsh -f /$CLEAR_TEST_TABLE
