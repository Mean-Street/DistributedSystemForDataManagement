#!/bin/bash

if [[ -z "$1" ]]
then
    echo "Usage: ./run_docker.sh scala-class [class-args]"
    echo "Example: ./run_docker.sh BasicTest"
    echo "Example: ./run_docker.sh BasicTestWithArgs arg0 arg1"
    exit 1
fi

docker pull epahomov/docker-spark:lightweighted
if ! sbt assembly; then
  exit 1
fi

class="$1"
shift
docker run --name sparkContainer -it -p 4040:4040 -v $(pwd)/target/scala-2.11/SDTD-assembly-1.0.jar:/sdtd.jar -v $(pwd)/data/lines.txt:/data.txt -v $(pwd)/conf:/conf epahomov/docker-spark:lightweighted /spark/bin/spark-submit --class "$class" --properties-file /conf/spark-defaults.conf /sdtd.jar $@
