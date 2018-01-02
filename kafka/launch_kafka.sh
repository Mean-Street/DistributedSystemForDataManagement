
echo "zookeeper.connect=$zookeeperhost:$zookeeperport" >> /opt/kafka/config/server.properties
echo "advertised.listeners=PLAINTEXT://$kafkahost:$kafkaport" >> /opt/kafka/config/server.properties
start-kafka.sh
