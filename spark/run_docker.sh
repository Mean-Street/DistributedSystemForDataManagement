#!/bin/sh

if [[ -z "$1" ]]
then
    echo "Usage: ./run_docker.sh scala-class"
    echo "Example: ./run_docker.sh BasicTest"
    exit 1
fi

docker pull epahomov/docker-spark:lightweighted
if ! sbt package; then
  exit 1
fi
docker run -it -p 4040:4040 -v $(pwd)/target/scala-2.11/sdtd_2.11-1.0.jar:/sdtd.jar -v $(pwd)/data/lines.txt:/data.txt -v $(pwd)/conf:/conf epahomov/docker-spark:lightweighted /spark/bin/spark-submit --class "$1" --properties-file /conf/spark-defaults.conf /sdtd.jar
