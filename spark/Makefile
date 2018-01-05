build:
	docker build . -t sdtdensimag/spark --no-cache

push:
	docker login -u=sdtdensimag
	docker push sdtdensimag/spark

data_preprocessing:
	./run_docker.sh DataPreprocessing $(KAFKA_HOST):$(KAFKA_PORT) $(KAFKA_CONSUMER_GROUP) $(KAFKA_TOPICS) $(KAFKA_NUM_THREADS) $(SPARK_PULLING_PERIOD)

run:
	@spark-submit --master local[4] --class "DataCleaning" --packages datastax:spark-cassandra-connector:2.0.1-s_2.11 --properties-file conf/spark-defaults.conf target/scala-2.11/sdtd_2.11-1.0.jar

