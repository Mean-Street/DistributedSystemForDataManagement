#!/bin/sh

docker pull epahomov/docker-spark:lightweighted
if ! sbt package; then
  exit 1
fi
docker run -it -p 4040:4040 --link spark_master:spark_master --link some-cassandra:some-cassandra -v $(pwd)/target/scala-2.11/sdtd_2.11-1.0.jar:/sdtd.jar -v $(pwd)/data/lines.txt:/data.txt -v $(pwd)/conf:/conf epahomov/docker-spark:lightweighted /spark/bin/spark-submit --class "DataCleaning" --packages datastax:spark-cassandra-connector:2.0.1-s_2.11 --properties-file /conf/spark-defaults.conf /sdtd.jar
