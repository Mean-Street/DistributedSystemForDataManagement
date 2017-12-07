# Kafka

## Deployment on AWS

* Create an EC2 instance for Zookeeper
* Open the ports `2181`, `2888` and `3888` in the security group of the instance
* Connect through SSH and run:

```bash
sudo apt-get update
sudo apt-get install docker.io
git clone https://github.com/Mean-Street/DistributedSystemForDataManagement sdtd
cd sdtd/zookeeper
# Open port 2181 in the firewall
sudo ufw disable # TODO: something cleaner
# On CentOS:
# firewall-cmd --zone=trusted --add-interface=docker0
```

* Create an EC2 instance for Kafka (at least 2go of RAM)
* Open the port `9092` in the security group of the instance
* Connect through SSH and run:

```bash
sudo apt-get update
sudo apt-get install docker.io
git clone https://github.com/Mean-Street/DistributedSystemForDataManagement sdtd
cd sdtd/kafka
```

* Edit the following line in server.properties: `zookeeper.connect=<ZOOKEEPER_IP>:2181`

* Run Zookeeper on its EC2 instance

```bash
sudo ./run_zookeeper.sh 2181
```

* Run Kafka on its EC2 instance

```bash
export ZOOKEEPER_HOST=172.31.2.242 # TODO
export KAFKA_HOST=172.31.29.232 # TODO
sudo ./run_kafka.sh $KAFKA_HOST 9092 ZOOKEEPER_HOST 2181 test:1:1
```
