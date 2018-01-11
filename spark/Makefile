build: compile
	docker build . -t sdtdensimag/spark --no-cache

compile:
	export SBT_OPTS="-Xms512M -Xmx3536M -Xss1M  -XX:+CMSClassUnloadingEnabled -XX:+UseConcMarkSweepGC -XX:MaxPermSize=724M"; cd app; sbt assembly

push:
	docker login -u=sdtdensimag
	docker push sdtdensimag/spark

test:
	./run_docker.sh Test arg0 arg1

data_preprocessing:
	./run_docker.sh DataPreprocessing $(KAFKA_HOST):$(KAFKA_PORT) $(KAFKA_CONSUMER_GROUP) $(KAFKA_TOPICS) $(KAFKA_NUM_THREADS) $(SPARK_PULLING_PERIOD)

twitter_preprocessing:
	./run_docker.sh TwitterPreprocessing

local_twitter:
	docker run -it -p 4040:4040 -v $(pwd)/app/target/scala-2.11/SDTD-assembly-1.0.jar:/SDTD-assembly-1.0.jar epahomov/docker-spark /spark/bin/spark-submit --class TwitterPreprocessing /SDTD-assembly-1.0.jar
