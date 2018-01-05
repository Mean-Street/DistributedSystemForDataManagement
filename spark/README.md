# Spark

* Install Docker
* Customize `env.sh`
* Edit `app/conf/spark-defaults.conf`:

```
spark.cassandra.connection.host <CASSANDRA_IP>
```

* Run:

```
source env.sh
make data_preprocessing
```
