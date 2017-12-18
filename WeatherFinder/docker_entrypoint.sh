#! /bin/sh

#===--- Stazrt script for the docker container ---===

mvn exec:java -Dexec.args="10.0.0.200 1234 randomstring"
