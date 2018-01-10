#! /bin/sh

#===--- Stazrt script for the docker container ---===

mvn exec:java -Dexec.args="${KAFKA_IP} ${KAFKA_PORT} ${KAFKA_TOPICS}"
