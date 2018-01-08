HOST_IP=10.0.0.101

sudo docker run -d \
-p 2181:2181 \
-p 2888:2888 \
-p 3888:3888 \
garland/zookeeper

sudo docker run --net="host" \
-p 5050:5050 \
-e "MESOS_HOSTNAME=${HOST_IP}" \
-e "MESOS_IP=${HOST_IP}" \
-e "MESOS_ZK=zk://${HOST_IP}:2181/mesos" \
-e "MESOS_PORT=5050" \
-e "MESOS_LOG_DIR=/var/log/mesos" \
-e "MESOS_QUORUM=1" \
-e "MESOS_REGISTRY=in_memory" \
-e "MESOS_WORK_DIR=/var/lib/mesos" \
-d \
garland/mesosphere-docker-mesos-master

sudo docker run \
-d \
-p 8080:8080 \
garland/mesosphere-docker-marathon --master zk://${HOST_IP}:2181/mesos --zk zk://${HOST_IP}:2181/marathon

sudo docker run -d \
--name mesos_slave_1 \
--entrypoint="mesos-slave" \
--ip=10.0.0.201 \
-e "MESOS_MASTER=zk://10.0.0.101:2181/mesos" \
-e "MESOS_LOG_DIR=/var/log/mesos" \
-e "MESOS_LOGGING_LEVEL=INFO" \
garland/mesosphere-docker-mesos-master:latest


sudo docker run -d --net=host -e PORTS=9090 mesosphere/marathon-lb sse --group=* --marathon=http://10.0.0.100:8080


sudo docker run --name smack-kafka -it --rm -e KAFKA_ZOOKEEPER_CONNECT=172.17.0.1:2182 -e KAFKA_ADVERTISED_HOST_NAME=172.17.0.1 -e KAFKA_ADVERTISED_PORT=9092 -e KAFKA_CREATE_TOPICS="topic:1:1" -p 9092:9092 sdtdensimag/kafka^
