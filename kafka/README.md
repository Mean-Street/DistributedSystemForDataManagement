# Kafka

## Deployment on AWS

* Deploy Zookeeper (see `../zookeeper/README.md`)
* Create an EC2 instance (Ubuntu, at least small, micro is not sufficient)
* Open the port `9092` in the security group of the instance
* Connect through SSH and run:

```bash
sudo apt-get update
sudo apt-get install -y docker.io

# Open ports in the firewall
sudo ufw disable # TODO: something cleaner

git clone https://github.com/Mean-Street/DistributedSystemForDataManagement sdtd
cd sdtd/kafka
```

* Edit the following line in `server.properties`: `zookeeper.connect=<ZOOKEEPER_IP>:2181`

* Run Kafka

```bash
sudo ./run_kafka.sh <KAFKA_IP> 9092 <ZOOKEEPER_IP> 2181 test:1:1
```

* Check if it is running:

```bash
# On another machine:
sudo apt-get install -y nmap
nmap -p 9092 -Pn <KAFKA_IP>
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
sbt "runMain Producer test <KAFKA_IP>:9092"
sbt "runMain Consumer test group-test <KAFKA_IP>:9092"
```
