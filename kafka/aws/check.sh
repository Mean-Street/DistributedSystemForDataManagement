#!/bin/bash

KAFKA_IP=$1
KAFKA_PORT=$2

sudo apt-get install -y nmap
nmap -p $KAFKA_PORT -Pn $KAFKA_IP
# PORT     STATE SERVICE
# 9092/tcp open  unknown

# Install sbt: http://www.scala-sbt.org/1.0/docs/Installing-sbt-on-Linux.html
echo "deb https://dl.bintray.com/sbt/debian /" | sudo tee -a /etc/apt/sources.list.d/sbt.list
sudo apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 2EE0EA64E40A89B84B2DF73499E82A75642AC823
sudo apt-get update
sudo apt-get install -y default-jre sbt

# Run the producer
cd ~/sdtd/kafka
sbt package
sbt "runMain Producer test $KAFKA_IP:$KAFKA_PORT"
sbt "runMain Consumer test group-test $KAFKA_IP:$KAFKA_PORT"
