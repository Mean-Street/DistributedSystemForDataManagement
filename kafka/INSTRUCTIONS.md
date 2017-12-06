# KAFKA instructions

## Start kafka docker
sudo dockerd
sudo docker-compose-single-broker.yml up

## Start a KAFKA shell
<!--sudo ./start-kafka-shell.sh 192.168.1.72 192.168.1.72:2182-->
sudo ./start-kafka-shell.sh \<DOCKER\_HOST\_IP> \<ZK\_HOST:ZK\_PORT> 
