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

## Test code locally
 cd smack/spark/app
 sbt assembly
sudo docker run -it -p 4040:4040 -v $(pwd)/target/scala-2.11/SDTD-assembly-1.0.jar:/SDTD-assembly-1.0.jar epahomov/docker-spark /spark/bin/spark-submit --class YourClassToBeRun /SDTD-assembly-1.0.jar

## On code update

When the code is updated, you need to rebuild the image to test the code:

```
make build_dev
make test
```

Once the code is tested, build the production image and push it:

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
