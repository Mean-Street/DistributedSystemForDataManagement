# Cassandra

## Deployment on AWS

* Create an EC2 instance for Cassandra
* Connect through SSH and run:

```bash
sudo apt-get update
sudo apt-get install docker.io

# Start Cassandra
./run_docker.sh

# Initialize table
# Connect to cqlsh prompt:
docker run -it --link cassandraContainer:cassandra --rm cassandra cqlsh cassandra
# Once in cqlsh, write:
create keyspace sdtd with replication = {'class':'SimpleStrategy', 'replication_factor':1};
create table sdtd.temperatures (id timeuuid primary key, date text, temperature double);
# Exit cqlsh

# Configure port 9042 for TCP on AWS
```
