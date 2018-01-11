FROM epahomov/docker-spark:lightweighted

ENV CONF_DIR /conf
ENV JAR_PATH /smack.jar

COPY ./conf ${CONF_DIR}
COPY ./target/scala-2.11/client-assembly-1.0.jar ${JAR_PATH}
COPY ./docker/run-spark /usr/bin/run-spark
