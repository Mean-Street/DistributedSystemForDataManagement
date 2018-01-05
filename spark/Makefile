build:
	docker build . -t sdtdensimag/spark --no-cache

push:
	docker login -u=sdtdensimag
	docker push sdtdensimag/spark

data_preprocessing:
	./run_docker.sh DataPreprocessing $(KAFKA_HOST):$(KAFKA_PORT) $(KAFKA_CONSUMER_GROUP) $(KAFKA_TOPICS) $(KAFKA_NUM_THREADS) $(SPARK_PULLING_PERIOD)
