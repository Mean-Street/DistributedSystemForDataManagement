#! /bin/sh
ls
mvn compile
mvn exec:java -Dexec.args="10.0.0.200 1234 randomstring"


#sudo docker build . -t test_akka
#sudo docker run --attach STDOUT test_akka
