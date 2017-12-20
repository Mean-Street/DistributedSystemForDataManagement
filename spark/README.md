# Spark

## Deployment on AWS

* Follow the instructions in `../kafka/README.md`
* Follow the instructions in `../cassandra/README.md`
* Create an EC2 instance (Ubuntu, micro is sufficient)
* Connect through SSH and run:

```bash
git clone https://github.com/Mean-Street/DistributedSystemForDataManagement sdtd
cd sdtd/spark
./init-ec2.sh
```

* Edit `conf/spark-defaults.conf`:

```
spark.cassandra.connection.host <CASSANDRA_IP>
```

```bash
# Run data preprocessing
sudo ./run_data_preprocessing.sh <KAFKA_IP>:9092
```
