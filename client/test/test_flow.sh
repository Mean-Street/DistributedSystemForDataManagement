#!/bin/bash

if [[ -z "$1" ]]; then
    echo "Usage: ./test_flow.sh <client_host>"
    echo "Example: ./test_flow.sh localhost"
    exit 1
fi

CLIENT_HOST="$1"

curl -H "Content-Type: application/json" -X POST -d '{"begin":"2018-01-01 02","end":"2018-01-01 03"}' "http://$CLIENT_HOST:8080/test"
