#!/bin/bash

if [[ -z "$1" ]]
then
    echo "Usage: ./run_docker.sh scala-class [class-args]"
    echo "Example: ./run_docker.sh BasicTest"
    echo "Example: ./run_docker.sh BasicTestWithArgs arg0 arg1"
    exit 1
fi

class="$1"
shift
docker run --name spark --rm -it -p 4040:4040 spark-smack /spark/bin/spark-submit --class "$class" --properties-file /conf/spark-defaults.conf /sdtd.jar $@
