#!/bin/sh

cd "$REPO/spark/app"
if ! sbt assembly; then
  exit 1
fi
