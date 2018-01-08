#! /bin/sh

ssh -n -i TestMesos.pem -o StrictHostKeyChecking=no ubuntu@52.36.251.4 sudo sh SDTD/mesos/launch_slave.sh &
