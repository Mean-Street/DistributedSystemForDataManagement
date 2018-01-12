build: compile
	docker build . -t sdtensimag/spark-client --no-cache

compile:
	sbt assembly

push:
	docker login -u=sdtensimag
	docker push sdtensimag/spark-client

run:
	#./run.sh $(cassandra_host) $(cassandra_port) $(main_class)
	./run.sh $(cassandra_host) $(cassandra_port) server.AkkaHttpServer
