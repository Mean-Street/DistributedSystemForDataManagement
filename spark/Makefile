all:
	@sbt package

run:
	@spark-submit --master local[4] --class Main --packages datastax:spark-cassandra-connector:2.0.1-s_2.11 target/scala-2.11/simple-project_2.11-1.0.jar
