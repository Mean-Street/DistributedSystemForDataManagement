#===--- Build and push the app image to the DockerHub repo ---===

APP_NAME=weatherfinder
DOCKER_ID_USER=sdtdensimag

all: build push

build:
	sudo docker build . -t ${APP_NAME}

run:
	sudo docker run --attach STDOUT --interactive=true -e KAFKA_IP=172.17.0.1 -e KAFKA_PORT=9092 -e KAFKA_TOPIC=temperature ${APP_NAME}

login:
	# Login to Docker Hub. Need to enter the password.
	sudo docker login -u=${DOCKER_ID_USER}

push:
	# Tag the image
	sudo docker tag ${APP_NAME} ${DOCKER_ID_USER}/${APP_NAME}
	# Push the image
	sudo docker push ${DOCKER_ID_USER}/${APP_NAME}
