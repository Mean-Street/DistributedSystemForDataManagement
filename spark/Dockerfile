FROM spark-base

ENV CONF_DIR /smack/spark/app/conf
ENV JAR_PATH /smack/spark/app/target/scala-2.11/SDTD-assembly-1.0.jar

RUN git clone https://github.com/Mean-Street/DistributedSystemForDataManagement /smack
WORKDIR /smack/spark/app
RUN sbt assembly
WORKDIR /smack/spark
RUN cp run-spark /usr/bin
