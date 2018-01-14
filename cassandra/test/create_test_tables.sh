#!/bin/bash

if [[ -z "$1" ]]; then
    echo "Usage: ./create_test_tables.sh <cassandra_docker_name>"
    echo "Example: ./create_test_tables.sh cassandraContainer"
    exit 1
fi

# Should be run from client module
TEST_DIR="../cassandra/test"
CREATE_TEST_TABLE="create_test_tables.cql"
CLEAR_TEST_TABLE="clear_test_tables.cql"

DOCKER_NAME="$1"

docker cp $TEST_DIR/$CREATE_TEST_TABLE $DOCKER_NAME:/
docker cp $TEST_DIR/$CLEAR_TEST_TABLE $DOCKER_NAME:/
docker exec $DOCKER_NAME cqlsh -f /$CREATE_TEST_TABLE
