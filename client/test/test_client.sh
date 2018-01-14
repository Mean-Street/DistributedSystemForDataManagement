#!/bin/sh

if [[ -z "$1" ]]; then
    echo "Usage: ./test_client.sh <client_host>"
    echo "Example: ./test_client.sh localhost"
    exit 1
fi

CLIENT_HOST="$1"

curl --request GET "http://$CLIENT_HOST:8080/example"
