#===--- Build and push the app image to the DockerHub repo ---===

APP_NAME=cassandra
DOCKER_NAME=cassandraContainer
DOCKER_ID_USER=sdtdensimag

all: build push

build:
	sudo docker build . -t ${APP_NAME}

run:
	sudo docker run --rm --network="host" --name ${DOCKER_NAME} -p 9042:9042 -p 7000:7000 -p 7199:7199 -e MAX_HEAP_SIZE="400M" -e HEAP_NEWSIZE="100M" ${DOCKER_ID_USER}/${APP_NAME}

run_slave:
	sudo docker run --rm --network="host" --name cassandra0 -p 9042:9042 -p 7000:7000 -p 7199:7199 -e MAX_HEAP_SIZE="400M" -e HEAP_NEWSIZE="100M" -e CASSANDRA_SEEDS="ADD_SEED_IP" ${DOCKER_ID_USER}/${APP_NAME}

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


