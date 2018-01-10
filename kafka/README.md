# Kafka

**Warning:** do not use `localhost` for hosts but ip addresses.

```
./run.sh <KAFKA_HOST_IP> 9092 <ZOOKEEPER_IP> 2181 <TOPIC_NAME>:<N_PARTITIONS>:<N_REPLICAS>
# Example:
# ./run.sh 192.168.4.41 9092 192.168.4.41 2181 topic:1:1
```
