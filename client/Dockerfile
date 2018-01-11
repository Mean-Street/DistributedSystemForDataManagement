# # Scala and sbt Dockerfile # # https://github.com/hseeberger/scala-sbt # 
# Pull base image 
FROM openjdk:8u151

#Arguments
ARG sparkAddress=127.0.0.1
ARG sparkPort=4040

# Env variables 
ENV SCALA_VERSION 2.12.4 
ENV SBT_VERSION 1.0.4 

# Scala expects this file 
RUN touch /usr/lib/jvm/java-8-openjdk-amd64/release

# Install Scala ## Piping curl directly in tar 
RUN \
   curl -fsL https://downloads.typesafe.com/scala/$SCALA_VERSION/scala-$SCALA_VERSION.tgz | tar xfz - -C /root/ \
&& echo >> /root/.bashrc \
&& echo "export PATH=~/scala-$SCALA_VERSION/bin:$PATH" >> /root/.bashrc

# Install sbt 
RUN \
   curl -L -o sbt-$SBT_VERSION.deb https://dl.bintray.com/sbt/debian/sbt-$SBT_VERSION.deb \
&& dpkg -i sbt-$SBT_VERSION.deb \
&& rm sbt-$SBT_VERSION.deb \
&& apt-get update \
&& apt-get install sbt \
&& sbt sbtVersion

# Define working directory 
WORKDIR /root

# Sparse checkout git repo
RUN \
	git init src \
&&	cd src \
&&	git remote add -f origin https://github.com/Mean-Street/DistributedSystemForDataManagement \
&&	git config core.sparseCheckout true \
&&	echo "client" >> .git/info/sparse-checkout \
&&	git pull origin master

# if sparse checkout not working
# RUN \
# 	git clone https://github.com/Mean-Street/DistributedSystemForDataManagement src

# compile & run
RUN \
	cd src/client \
&&  git checkout test_client \
&&	sbt compile

EXPOSE 8080

WORKDIR /root/src/client

CMD sbt "run $sparkAddress $sparkPort" \
#&&  tail -f /dev/null #keep the container running as it sometimes stop
