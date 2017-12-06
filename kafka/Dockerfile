FROM anapsix/alpine-java

ARG kafka_version=1.0.0
ARG scala_version=2.12

RUN apk add --update unzip wget curl docker jq coreutils

ENV KAFKA_VERSION=$kafka_version
ENV SCALA_VERSION=$scala_version

RUN touch /tmp/download-kafka.sh
RUN echo "#!/bin/sh" >> /tmp/download-kafka.sh
RUN echo "mirror=$(curl --stderr /dev/null https://www.apache.org/dyn/closer.cgi\?as_json\=1 | jq -r '.preferred')" >> /tmp/download-kafka.sh
RUN echo "url=\"\${mirror}kafka/${KAFKA_VERSION}/kafka_${SCALA_VERSION}-${KAFKA_VERSION}.tgz\"" >> /tmp/download-kafka.sh
RUN echo "wget -q \"\${url}\" -O \"/tmp/kafka_${SCALA_VERSION}-${KAFKA_VERSION}.tgz\"" >> /tmp/download-kafka.sh

RUN chmod a+x /tmp/download-kafka.sh
RUN sync
RUN cat /tmp/download-kafka.sh 
RUN /tmp/download-kafka.sh 
RUN tar xfz /tmp/kafka_${SCALA_VERSION}-${KAFKA_VERSION}.tgz -C /opt
RUN rm /tmp/kafka_${SCALA_VERSION}-${KAFKA_VERSION}.tgz
RUN ln -s /opt/kafka_${SCALA_VERSION}-${KAFKA_VERSION} /opt/kafka

VOLUME ["/kafka"]

ENV KAFKA_HOME /opt/kafka
ENV PATH ${PATH}:${KAFKA_HOME}/bin

RUN touch /usr/bin/start-kafka.sh
RUN echo "#!/bin/sh" >> /usr/bin/start-kafka.sh
RUN echo ""exec $KAFKA_HOME/bin/kafka-server-start.sh /server.properties >> /usr/bin/start-kafka.sh
RUN chmod a+x /usr/bin/start-kafka.sh

EXPOSE 9092

CMD ["start-kafka.sh"]
