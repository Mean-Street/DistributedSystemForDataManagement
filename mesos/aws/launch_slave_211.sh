#! /bin/sh

ssh -n -i TestMesos.pem -o StrictHostKeyChecking=no ubuntu@34.212.245.184 sudo sh SDTD/mesos/launch_slave.sh &
