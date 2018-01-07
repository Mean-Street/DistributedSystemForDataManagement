MASTER_IP=10.0.0.100

sudo service zookeeper restart
sudo service mesos-master restart
marathon --master zk://${MASTER_IP}:2181/mesos --zk zk://${MASTER_IP}:2181/marathon
