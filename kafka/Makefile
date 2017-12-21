# Requirement:
# * Define a bash variable $ZOOKEEPER_PORT

APP_NAME=kafka
DOCKER_ID_USER=sdtdensimag

all: build push

build:
	sudo docker build . -t ${APP_NAME}

run:
	sudo docker run --rm -e KAFKA_ZOOKEEPER_CONNECT=${ZOOKEEPER_HOST}:${ZOOKEEPER_PORT} \
	-e KAFKA_ADVERTISED_HOST_NAME=${KAFKA_HOST} -e KAFKA_CREATE_TOPICS="${TOPICS}" \
	-p ${KAFKA_PORT}:9092 -v $(pwd)/server.properties:/opt/kafka/config/server.properties \
	${APP_NAME}

login:
	# Login to Docker Hub. Need to enter the password.
	sudo docker login -u=${DOCKER_ID_USER}

push:
	# Tag the image
	sudo docker tag ${APP_NAME} ${DOCKER_ID_USER}/${APP_NAME}
	# Push the image
	sudo docker push ${DOCKER_ID_USER}/${APP_NAME}
