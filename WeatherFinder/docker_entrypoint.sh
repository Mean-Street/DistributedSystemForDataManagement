#! /bin/sh

#===--- Stazrt script for the docker container ---===

mvn exec:java -Dexec.args="172.17.0.1 9092 test"
