# KAFKA instructions

## Setup the configuration files
If using the default .yml, change the KAFKA\_ADVERTISED\_HOST\_NAME to the docker host IP.
Set the zookeeper variable to the KAFKA host IP.

## Start kafka docker
sudo dockerd
sudo docker-compose -f docker-compose-single-broker.yml up

## Start a KAFKA shell
<!--sudo ./start-kafka-shell.sh 192.168.1.72 192.168.1.72:2182-->
sudo ./start-kafka-shell.sh \<DOCKER\_HOST\_IP> \<ZK\_HOST:ZK\_PORT> 

## Create topics and KAFKA producers and consumers with SCALA/JAVA
check README.md of kafka\_scala
