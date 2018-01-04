FROM epahomov/docker-spark

ENV REPO /smack

RUN cd /usr/lib/apt/methods; ln -s http https
RUN apt-get update
RUN apt-get install -y apt-transport-https

# Install sbt: http://www.scala-sbt.org/1.0/docs/Installing-sbt-on-Linux.html
RUN mkdir -p /etc/apt/sources.list.d
RUN > /etc/apt/sources.list.d/sbt.list
RUN echo "deb https://dl.bintray.com/sbt/debian /" | tee -a /etc/apt/sources.list.d/sbt.list
RUN apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 2EE0EA64E40A89B84B2DF73499E82A75642AC823
RUN apt-get update
RUN apt-get install -y git default-jre sbt

RUN git clone https://github.com/Mean-Street/DistributedSystemForDataManagement $REPO
RUN cp "$REPO/spark/docker-entrypoint.sh" /usr/bin/local

ENTRYPOINT ["docker-entrypoint.sh"]
