name := "Simple Project"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
	"org.apache.spark" %% "spark-sql" % "2.2.0",
	"com.datastax.spark" %% "spark-cassandra-connector" % "2.0.6"
)

