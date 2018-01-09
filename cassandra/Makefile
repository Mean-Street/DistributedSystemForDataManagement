#===--- Build and push the app image to the DockerHub repo ---===

APP_NAME=cassandra
DOCKER_NAME=cassandraContainer
DOCKER_ID_USER=sdtdensimag

all: build push

build:
	sudo docker build . -t ${APP_NAME}

runseed:
	sudo docker run --name ${DOCKER_NAME} -p 9042:9042 -p 7000:7000 -p 7199:7199 -e MAX_HEAP_SIZE="400M" -e HEAP_NEWSIZE="100M" ${DOCKER_ID_USER}/${APP_NAME}

run:
<<<<<<< HEAD
	sudo docker run --rm --name ${DOCKER_NAME} -p 9042:9042 ${DOCKER_ID_USER}/${APP_NAME}
=======
	sudo docker run --name cassandra0 -p 9052:9042 -p 7010:7000 -p 7209:7199 -e MAX_HEAP_SIZE="400M" -e HEAP_NEWSIZE="100M" -e CASSANDRA_SEEDS="172.17.0.2" ${DOCKER_ID_USER}/${APP_NAME}
>>>>>>> 8e93b1f3a9cdf7c5fec5bdd9ee3d14a158612958

login:
	# Login to Docker Hub. Need to enter the password.
	sudo docker login -u=${DOCKER_ID_USER}

push:
	# Tag the image
	sudo docker tag ${APP_NAME} ${DOCKER_ID_USER}/${APP_NAME}
	# Push the image
	sudo docker push ${DOCKER_ID_USER}/${APP_NAME}

clean:
	sudo docker rm -f ${DOCKER_NAME}

cqlsh:
	sudo docker exec -it ${DOCKER_NAME} cqlsh

check:
	sudo docker exec -it ${DOCKER_NAME} nodetool status


