FROM cassandra
COPY ./launch_cassandra.sh /tmp/SDTD/cassandra/launch_cassandra.sh
COPY ./createTable.cql /tmp/SDTD/cassandra/createTable.cql

ENTRYPOINT ["/bin/bash"]
CMD ["/tmp/SDTD/cassandra/launch_cassandra.sh", "/tmp/SDTD/cassandra/createTable.cql"]
