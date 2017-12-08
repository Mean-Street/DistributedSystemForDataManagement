#!/bin/bash

docker pull maven
docker run -it --rm --name akka-server -v "$PWD":/usr/src/akka_server -w /usr/src/akka_server maven mvn compile
docker run -it --rm --name akka-server -v "$PWD":/usr/src/akka_server -w /usr/src/akka_server maven mvn exec:java -Dexec.args="$1 $2 $3"

