#! /bin/sh

ssh -n -i TestMesos.pem -o StrictHostKeyChecking=no ubuntu@52.11.199.130 sudo sh SDTD/mesos/launch_slave.sh &
