# Spark

## Deployment on AWS

* Follow the instructions in `../kafka/README.md`
* Create an EC2 instance for Spark
* Connect through SSH and run:

```bash
sudo apt-get update
sudo apt-get install docker.io

# Install sbt
echo "deb https://dl.bintray.com/sbt/debian /" | sudo tee -a /etc/apt/sources.list.d/sbt.list
sudo apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 2EE0EA64E40A89B84B2DF73499E82A75642AC823
sudo apt-get update
sudo apt-get install sbt

# Install Java
sudo apt-get install default-jre

# Run data preprocessing
sudo ./run_data_preprocessing.sh <ZOOKEEPER_IP>:2181
# Or, maybe:
sudo ./run_data_preprocessing.sh <KAFKA_IP>:9092
```
