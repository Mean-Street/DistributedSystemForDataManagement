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

* Run Kafka

```bash
. ./config.sh
make run
```

* Check if it is running:

```bash
# On another machine:
./check.sh KAFKA_IP 9092
```
