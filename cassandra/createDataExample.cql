drop keyspace sdtd;
create keyspace sdtd with replication = {'class':'SimpleStrategy', 'replication_factor':1};

create table sdtd.weatherFromCQL (
		id int primary key,
		city text,
		temperature int
);

insert into sdtd.weatherFromCQL (id, city, temperature) values (1, 'New York', 22);
insert into sdtd.weatherFromCQL (id, city, temperature) values (2, 'Paris', 14);
insert into sdtd.weatherFromCQL (id, city, temperature) values (3, 'Tokyo', 11);
insert into sdtd.weatherFromCQL (id, city, temperature) values (4, 'Londres', 14);
insert into sdtd.weatherFromCQL (id, city, temperature) values (5, 'Dubai', 31);
insert into sdtd.weatherFromCQL (id, city, temperature) values (6, 'Moscou', 6);

select * from sdtd.weatherFromCQL;

