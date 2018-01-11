CREATE KEYSPACE IF NOT EXISTS sdtd WITH replication = {'class':'SimpleStrategy', 'replication_factor':1};

CREATE TABLE IF NOT EXISTS sdtd.temperature (
		source text,
		date text,
		temperature double,
		PRIMARY KEY (source, date)
);

CREATE TABLE IF NOT EXISTS sdtd.tweet (
		id text,
		tweet text,
		sentiment text,
		date text,
		PRIMARY KEY (id)
);
