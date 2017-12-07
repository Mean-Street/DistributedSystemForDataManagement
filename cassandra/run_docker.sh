#!/bin/sh
docker pull cassandra
docker run -p 9042:9042 --name cassandraContainer -d cassandra:latest
