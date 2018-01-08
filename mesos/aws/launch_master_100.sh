#! /bin/sh

ssh -n -i TestMesos.pem -o StrictHostKeyChecking=no ubuntu@52.33.64.166 sudo sh SDTD/mesos/launch_master.sh &
