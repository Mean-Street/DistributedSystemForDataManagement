# Kafka

## Deployment on AWS

* Deploy Zookeeper (see `../zookeeper/README.md`)
* Create an EC2 instance (Ubuntu, at least small, micro is not sufficient)
* Open the port `9092` in the security group of the instance
* Connect through SSH and run:

```bash
git clone https://github.com/Mean-Street/DistributedSystemForDataManagement sdtd
cd sdtd/kafka
./init-ec2.sh
```

* Edit the following lines in `server.properties`:

```
advertised.listeners=PLAINTEXT://<KAFKA_IP>:9092
zookeeper.connect=<ZOOKEEPER_IP>:2181
```

* Run Kafka

```bash
sudo ./run_kafka.sh <KAFKA_IP> 9092 <ZOOKEEPER_IP> 2181 test:1:1
```

* Check if it is running:

```bash
# On another machine:
./check.sh KAFKA_IP 9092
```
