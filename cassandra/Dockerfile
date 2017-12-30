FROM cassandra
COPY ./launch_cassandra.sh /tmp/SDTD/cassandra/launch_cassandra.sh
COPY ./createTable.cql /tmp/SDTD/cassandra/createTable.cql

CMD ["sh", "/tmp/SDTD/cassandra/launch_cassandra.sh", "/tmp/SDTD/cassandra/createTable.cql"]
