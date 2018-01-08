build:
	docker build . -t sdtdensimag/spark --no-cache

build_dev:
	docker build . -f Dockerfile_dev -t sdtdensimag/spark --no-cache

push:
	docker login -u=sdtdensimag
	docker push sdtdensimag/spark

test:
	./run_docker.sh Test arg0 arg1

data_preprocessing:
	./run_docker.sh DataPreprocessing $(KAFKA_HOST):$(KAFKA_PORT) $(KAFKA_CONSUMER_GROUP) $(KAFKA_TOPICS) $(KAFKA_NUM_THREADS) $(SPARK_PULLING_PERIOD)

twitter_preprocessing:
	./run_docker.sh TwitterPreprocessing
