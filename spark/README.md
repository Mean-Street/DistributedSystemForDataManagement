# Spark

## Deployment on AWS

* Follow the instructions in `../kafka/README.md`
* Follow the instructions in `../cassandra/README.md`
* Create an EC2 instance (Ubuntu, micro is sufficient)
* Connect through SSH and run:

```bash
# Install sbt: http://www.scala-sbt.org/1.0/docs/Installing-sbt-on-Linux.html
echo "deb https://dl.bintray.com/sbt/debian /" | sudo tee -a /etc/apt/sources.list.d/sbt.list
sudo apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 2EE0EA64E40A89B84B2DF73499E82A75642AC823
sudo apt-get update
sudo apt-get install -y docker.io default-jre sbt

git clone https://github.com/Mean-Street/DistributedSystemForDataManagement sdtd
cd sdtd/spark
```

* Edit `conf/spark-defaults.conf`:

```
spark.cassandra.connection.host <CASSANDRA_IP>
```

```bash
# Run data preprocessing
sudo ./run_data_preprocessing.sh <KAFKA_IP>:9092
```
