# Spark

```bash
make # Compile and create the image
make test
```

## Temperature

```bash
# "temperature" is the name of the topic. To be synchronized with Akka
# PULLING_PERIOD_SEC is typically 2 (in seconds)
./run.sh <CASSANDRA_HOST> <CASSANDRA_PORT> DataPreprocessing <KAFKA_HOST>:<KAFKA_PORT> temperature <PULLING_PERIOD_SEC>
```

## Tweets

TODO
