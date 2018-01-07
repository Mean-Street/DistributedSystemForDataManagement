# Cassandra

## Run Cassandra in a Docker
make build
make run

## Connect a cqlsh prompt to this Docker
make cqlsh

## Delete the Docker
make clean




## Deployment on AWS - WARNING => MIGHT BE OUT-OF-DATE !!!!

* Create an EC2 instance (Ubuntu, micro)
* Open port `9042`
* Connect through SSH and run:

```bash
git clone https://github.com/Mean-Street/DistributedSystemForDataManagement sdtd
cd sdtd/cassandra
./init-ec2.sh
sudo ./run_docker.sh

# Initialize table
# Connect to cqlsh prompt:
sudo docker run -it --link cassandraContainer:cassandra --rm cassandra cqlsh cassandra
# Once in cqlsh, write:
create keyspace sdtd with replication = {'class':'SimpleStrategy', 'replication_factor':1};
create table sdtd.temperatures (id timeuuid primary key, date text, temperature double);
# Exit cqlsh
```
