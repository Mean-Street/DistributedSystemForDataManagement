#!/bin/sh
docker run --rm -p 9042:9042 -p 7000:7000 -p 7001:7001 --name cassandraContainer -d sdtdensimag/cassandra
