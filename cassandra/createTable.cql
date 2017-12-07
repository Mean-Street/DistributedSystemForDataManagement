create keyspace sdtd with replication = {'class':'SimpleStrategy', 'replication_factor':1};

create table sdtd.temperatures (
		id int primary key,
		date text,
		temperature double
);

