#! /bin/sh

ssh -n -i TestMesos.pem -o StrictHostKeyChecking=no ubuntu@52.88.167.87 sudo sh SDTD/mesos/launch_slave.sh &
