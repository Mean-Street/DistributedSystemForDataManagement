MASTER_IP=10.0.0.100

sudo service mesos-slave restart
sudo docker run -d --net=host -e PORTS=9090 mesosphere/marathon-lb sse --group=* --marathon=http://${MASTER_IP}:8080
