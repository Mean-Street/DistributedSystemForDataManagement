#!/bin/sh

cd "$REPO/spark/app"

if ! sbt assembly; then
  exit 1
fi

cp target/scala-2.11/SDTD-assembly-1.0.jar /sdtd.jar
cp -r conf /conf

# Execute command passed to docker run
exec "$@"
