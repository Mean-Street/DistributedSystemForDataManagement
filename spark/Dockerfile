FROM epahomov/docker-spark:lightweighted

ENV CONF_DIR /conf
ENV JAR_PATH /smack.jar

COPY ./app/conf /conf
COPY ./app/target/scala-2.11/SDTD-assembly-1.0.jar /smack.jar
COPY ./docker/run-spark /usr/bin/run-spark
