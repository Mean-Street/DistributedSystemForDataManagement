#! /bin/sh
mvn compile
mvn exec:java -Dexec.args="0 1 2"
