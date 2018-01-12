build: compile
	docker build . -t sdtdensimag/spark-client --no-cache

compile:
	sbt assembly

push:
	docker login -u=sdtdensimag
	docker push sdtdensimag/spark-client

run:
	#./run.sh $(cassandra_host) $(cassandra_port) $(main_class)
	./run.sh $(cassandra_host) $(cassandra_port) server.AkkaHttpServer
