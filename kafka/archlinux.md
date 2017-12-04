# How to setup Kafka using archlinux

## Install
First install zookeeper from aur (out of date though).
Then install kafka from aur (SKIP validity check for kafka and change version).

## Services
sudo systemctl start kafka
sudo systemctl start zookeeper

## Create a topic
kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test
kafka-topics.sh --list --zookeeper localhost:2181

## Send messages
kafka-console-producer.sh --broker-list localhost:9092 --topic test

## Start a consumer
kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic test --from-beginning
