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

INSERT INTO sdtd.temperature ("source","date","temperature") VALUES ('source','2018-01-01 01:20', 0.5);
INSERT INTO sdtd.temperature ("source","date","temperature") VALUES ('source','2018-01-01 01:20', 0.5);
INSERT INTO sdtd.temperature ("source","date","temperature") VALUES ('source','2018-01-01 01:20', 2.5);
INSERT INTO sdtd.temperature ("source","date","temperature") VALUES ('source','2018-01-01 02:20', 1.5);
INSERT INTO sdtd.temperature ("source","date","temperature") VALUES ('source','2018-01-01 02:20', 3.5);
INSERT INTO sdtd.temperature ("source","date","temperature") VALUES ('source','2018-01-01 03:20', 4.5);
INSERT INTO sdtd.temperature ("source","date","temperature") VALUES ('source','2018-01-01 04:20', 5.5);
INSERT INTO sdtd.temperature ("source","date","temperature") VALUES ('source','2018-01-01 05:20', 6.5);
INSERT INTO sdtd.temperature ("source","date","temperature") VALUES ('source','2018-01-01 06:20', 7.5);
INSERT INTO sdtd.temperature ("source","date","temperature") VALUES ('source','2018-01-01 07:20', 8.5);
INSERT INTO sdtd.temperature ("source","date","temperature") VALUES ('source','2018-01-01 08:20', 8.5);
INSERT INTO sdtd.temperature ("source","date","temperature") VALUES ('source','2018-01-01 10:20', 9.5);
INSERT INTO sdtd.temperature ("source","date","temperature") VALUES ('source','2018-01-01 11:20', 10.5);
INSERT INTO sdtd.temperature ("source","date","temperature") VALUES ('source','2018-01-01 12:20', 11.5);
INSERT INTO sdtd.temperature ("source","date","temperature") VALUES ('source','2018-01-01 13:20', 12.5);
INSERT INTO sdtd.temperature ("source","date","temperature") VALUES ('source','2018-01-01 14:20', 10.5);
INSERT INTO sdtd.temperature ("source","date","temperature") VALUES ('source','2018-01-01 15:20', 9.5);
INSERT INTO sdtd.temperature ("source","date","temperature") VALUES ('source','2018-01-01 16:20', 7.5);
INSERT INTO sdtd.temperature ("source","date","temperature") VALUES ('source','2018-01-01 17:20', 6.5);
INSERT INTO sdtd.temperature ("source","date","temperature") VALUES ('source','2018-01-01 18:20', 6.5);
INSERT INTO sdtd.temperature ("source","date","temperature") VALUES ('source','2018-01-01 19:20', 5.5);
INSERT INTO sdtd.temperature ("source","date","temperature") VALUES ('source','2018-01-01 20:20', 5.5);
INSERT INTO sdtd.temperature ("source","date","temperature") VALUES ('source','2018-01-01 21:20', 5.5);
INSERT INTO sdtd.temperature ("source","date","temperature") VALUES ('source','2018-01-01 22:20', 4.5);
INSERT INTO sdtd.temperature ("source","date","temperature") VALUES ('source','2018-01-01 23:20', 4.5);
INSERT INTO sdtd.temperature ("source","date","temperature") VALUES ('source','2018-01-02 00:20', 4.5);
INSERT INTO sdtd.temperature ("source","date","temperature") VALUES ('source','2018-01-02 01:20', 3.5);
INSERT INTO sdtd.temperature ("source","date","temperature") VALUES ('source','2018-01-02 02:20', 2.5);
INSERT INTO sdtd.temperature ("source","date","temperature") VALUES ('source','2018-01-02 03:20', 2.5);
INSERT INTO sdtd.temperature ("source","date","temperature") VALUES ('source','2018-01-02 04:20', 3.5);

INSERT INTO sdtd.tweet ("id", "tweet", "date", "sentiment") VALUES ('2','','2018-01-01 01:20', 'VERY_NEGATIVE');
INSERT INTO sdtd.tweet ("id", "tweet", "date", "sentiment") VALUES ('1','','2018-01-01 01:20', 'VERY_NEGATIVE');
INSERT INTO sdtd.tweet ("id", "tweet", "date", "sentiment") VALUES ('2','','2018-01-01 01:20', 'VERY_NEGATIVE');
INSERT INTO sdtd.tweet ("id", "tweet", "date", "sentiment") VALUES ('3','','2018-01-01 02:20', 'NEGATIVE');
INSERT INTO sdtd.tweet ("id", "tweet", "date", "sentiment") VALUES ('4','','2018-01-01 02:20', 'NEGATIVE');
INSERT INTO sdtd.tweet ("id", "tweet", "date", "sentiment") VALUES ('5','','2018-01-01 03:20', 'NEGATIVE');
INSERT INTO sdtd.tweet ("id", "tweet", "date", "sentiment") VALUES ('6','','2018-01-01 04:20', 'NEGATIVE');
INSERT INTO sdtd.tweet ("id", "tweet", "date", "sentiment") VALUES ('7','','2018-01-01 04:20', 'NEUTRAL');
INSERT INTO sdtd.tweet ("id", "tweet", "date", "sentiment") VALUES ('8','','2018-01-01 05:20', 'NEUTRAL');
INSERT INTO sdtd.tweet ("id", "tweet", "date", "sentiment") VALUES ('9','','2018-01-01 06:20', 'POSITIVE');
INSERT INTO sdtd.tweet ("id", "tweet", "date", "sentiment") VALUES ('10','','2018-01-01 07:20', 'POSITIVE');
INSERT INTO sdtd.tweet ("id", "tweet", "date", "sentiment") VALUES ('11','','2018-01-01 010:20', 'POSITIVE');
INSERT INTO sdtd.tweet ("id", "tweet", "date", "sentiment") VALUES ('12','','2018-01-01 11:20', 'VERY_POSITIVE');
INSERT INTO sdtd.tweet ("id", "tweet", "date", "sentiment") VALUES ('13','','2018-01-01 12:20', 'VERY_POSITIVE');
INSERT INTO sdtd.tweet ("id", "tweet", "date", "sentiment") VALUES ('14','','2018-01-01 13:20', 'VERY_POSITIVE');
INSERT INTO sdtd.tweet ("id", "tweet", "date", "sentiment") VALUES ('15','','2018-01-01 14:20', 'POSITIVE');
INSERT INTO sdtd.tweet ("id", "tweet", "date", "sentiment") VALUES ('16','','2018-01-01 15:20', 'POSITIVE');
INSERT INTO sdtd.tweet ("id", "tweet", "date", "sentiment") VALUES ('17','','2018-01-01 16:20', 'POSITIVE');
INSERT INTO sdtd.tweet ("id", "tweet", "date", "sentiment") VALUES ('18','','2018-01-01 17:20', 'POSITIVE');
INSERT INTO sdtd.tweet ("id", "tweet", "date", "sentiment") VALUES ('19','','2018-01-01 18:20', 'NEUTRAL');
INSERT INTO sdtd.tweet ("id", "tweet", "date", "sentiment") VALUES ('20','','2018-01-01 19:20', 'NEUTRAL');
INSERT INTO sdtd.tweet ("id", "tweet", "date", "sentiment") VALUES ('21','','2018-01-01 20:20', 'POSITIVE');
INSERT INTO sdtd.tweet ("id", "tweet", "date", "sentiment") VALUES ('22','','2018-01-01 21:20', 'NEGATIVE');
INSERT INTO sdtd.tweet ("id", "tweet", "date", "sentiment") VALUES ('23','','2018-01-01 22:20', 'NEGATIVE');
INSERT INTO sdtd.tweet ("id", "tweet", "date", "sentiment") VALUES ('24','','2018-01-01 23:20', 'NEUTRAL');
INSERT INTO sdtd.tweet ("id", "tweet", "date", "sentiment") VALUES ('25','','2018-01-02 00:20', 'NEGATIVE');
INSERT INTO sdtd.tweet ("id", "tweet", "date", "sentiment") VALUES ('26','','2018-01-02 01:20', 'VERY_NEGATIVE');
INSERT INTO sdtd.tweet ("id", "tweet", "date", "sentiment") VALUES ('27','','2018-01-02 02:20', 'NEGATIVE');
INSERT INTO sdtd.tweet ("id", "tweet", "date", "sentiment") VALUES ('28','','2018-01-02 03:20', 'VERY_NEGATIVE');
INSERT INTO sdtd.tweet ("id", "tweet", "date", "sentiment") VALUES ('29','','2018-01-02 04:20', 'VERY_NEGATIVE');
