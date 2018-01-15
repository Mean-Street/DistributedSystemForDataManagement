DOCKER_NAME    := clientContainer
DOCKER_ID_USER := sdtdensimag
APP_NAME       := spark-client

CLIENT_TEST_SCRIPT := ./test
TEST_CLIENT        := test_client.sh
TEST_FLOW          := test_flow.sh

CASSANDRA_TEST_SCRIPT := ../cassandra/test
CREATE_TABLES         := create_test_tables.sh
CLEAR_TABLES          := clear_test_tables.sh

build: compile
	docker build . -t ${DOCKER_ID_USER}/${APP_NAME} --no-cache

compile:
	sbt assembly

push:
	docker login -u=${DOCKER_ID_USER}
	docker push ${DOCKER_ID_USER}/${APP_NAME}

clean:
	docker rm -f ${DOCKER_NAME}

run:
	docker run --rm -it --name ${DOCKER_NAME} -p 4040:4040 -p 8080:8080 -e CASSANDRA_HOST="$(cassandra_host)" -e CASSANDRA_PORT="$(cassandra_port)" ${DOCKER_ID_USER}/${APP_NAME} run-spark server.AkkaHttpServer

test_client:
	$(CLIENT_TEST_SCRIPT)/$(TEST_CLIENT) $(client_host)

test_flow:
	$(CLIENT_TEST_SCRIPT)/$(TEST_FLOW) $(client_host)

create_test:
	$(CASSANDRA_TEST_SCRIPT)/$(CREATE_TABLES) $(cassandra_container)

clear_test:
	$(CASSANDRA_TEST_SCRIPT)/$(CLEAR_TABLES) $(cassandra_container)
