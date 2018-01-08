build: compile
	docker build . -t spark-client

compile:
	sbt assembly

run:
	./run.sh $(cassandra_host) $(cassandra_port) $(main_class)
