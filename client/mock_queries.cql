CREATE KEYSPACE sdtd WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1 };

CREATE TABLE sdtd.temperature (date text PRIMARY KEY, temperature double);
CREATE TABLE sdtd.tweet (date text PRIMARY KEY, sentiment text);

INSERT INTO sdtd.temperature ("date", "temperature") VALUES ('2018-01-01T01:20:20', 1.5);
INSERT INTO sdtd.temperature ("date", "temperature") VALUES ('2018-01-01T01:20:23', 1);
INSERT INTO sdtd.temperature ("date", "temperature") VALUES ('2018-01-01T01:20:25', 2);
INSERT INTO sdtd.temperature ("date", "temperature") VALUES ('2018-01-01T02:20:25', 2);
INSERT INTO sdtd.temperature ("date", "temperature") VALUES ('2018-01-01T02:20:27', 4);
INSERT INTO sdtd.temperature ("date", "temperature") VALUES ('2018-01-01T03:20:27', 4.5);
INSERT INTO sdtd.temperature ("date", "temperature") VALUES ('2018-01-01T04:20:27', 1.5);
INSERT INTO sdtd.temperature ("date", "temperature") VALUES ('2018-01-01T02:20:27', 7.5);

INSERT INTO sdtd.tweet ("date", "sentiment") VALUES ('2018-01-01T01:20:20', '2.5');
INSERT INTO sdtd.tweet ("date", "sentiment") VALUES ('2018-01-01T01:20:23', '3');
INSERT INTO sdtd.tweet ("date", "sentiment") VALUES ('2018-01-01T01:20:25', '2');
INSERT INTO sdtd.tweet ("date", "sentiment") VALUES ('2018-01-01T02:20:25', '3');
INSERT INTO sdtd.tweet ("date", "sentiment") VALUES ('2018-01-01T02:20:27', '2');
INSERT INTO sdtd.tweet ("date", "sentiment") VALUES ('2018-01-01T03:20:27', '8.5');
INSERT INTO sdtd.tweet ("date", "sentiment") VALUES ('2018-01-01T04:20:27', '4.5');
INSERT INTO sdtd.tweet ("date", "sentiment") VALUES ('2018-01-01T02:20:27', '3.5');
