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

## On code update

When the code is updated, you need to rebuild the image:

```
make build
make push
```

# Dockerfiles

* `Dockerfile-base` describes an image with both Spark and SBT installed to compile the code and run it
* `Dockerfile` describes an image which pulls the code and compile it

Because we call `git clone`, we need to build the image without cache, else `git clone`
won't be called. To avoid installing again all packages for Spark and SBT, I created
two Dockerfiles.
