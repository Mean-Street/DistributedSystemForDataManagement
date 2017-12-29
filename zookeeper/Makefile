# Requirement:
# * Define a bash variable $ZOOKEEPER_PORT

APP_NAME=zookeeper
DOCKER_ID_USER=sdtdensimag

all: build push

build:
	sudo docker build . -t ${APP_NAME}

run:
	sudo docker run --attach STDOUT --interactive=true -p ${ZOOKEEPER_PORT}:2181 ${APP_NAME}

login:
	# Login to Docker Hub. Need to enter the password.
	sudo docker login -u=${DOCKER_ID_USER}

push:
	# Tag the image
	sudo docker tag ${APP_NAME} ${DOCKER_ID_USER}/${APP_NAME}
	# Push the image
	sudo docker push ${DOCKER_ID_USER}/${APP_NAME}
