#===--- Build and push the app image to the DockerHub repo ---===

APP_NAME=cassandra
DOCKER_NAME=cassandraContainer
DOCKER_ID_USER=sdtdensimag

all: build push

build:
	sudo docker build . -t ${DOCKER_ID_USER}/${APP_NAME}

run:
	sudo docker run --name ${DOCKER_NAME} -p 9042:9042 ${DOCKER_ID_USER}/${APP_NAME} --rm

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
