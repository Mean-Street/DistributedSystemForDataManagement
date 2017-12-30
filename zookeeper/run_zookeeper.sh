#!/bin/bash

zooport=$1
docker run --name smack-zookeeper --rm -p $zooport:2181 -d sdtdensimag/zookeeper
