create keyspace sdtd with replication = {'class':'SimpleStrategy', 'replication_factor':1};

create table sdtd.temperatures (
		id timeuuid primary key,
		date text,
		temperature double
);

