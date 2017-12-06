### Install
- download cassandra and spark
- add their bin directories to path
- set CASSANDRA\_HOME et SPARK\_HOME

### Launch Http Server
Run :
    sbt httpServer

### Launch Cassandra instance
Run :
	cassandra -f
	cqlsh
		(in prompt, run: source 'cassandra/createData.cql' then exit)

### Launch Spark
Run :
	make && make run (in the spark directory)
