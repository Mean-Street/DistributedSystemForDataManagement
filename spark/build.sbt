name := "SDTD"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
	"org.apache.spark" %% "spark-sql" % "2.2.0",
	"com.datastax.spark" %% "spark-cassandra-connector" % "2.0.6",

  // Akka HTTP
  "com.typesafe.akka" %% "akka-http" % "10.0.11",
  "com.typesafe.akka" %% "akka-http-testkit" % "10.0.11" % Test,

  // Akka Stream
  "com.typesafe.akka" %% "akka-stream" % "2.5.7",
  "com.typesafe.akka" %% "akka-stream-testkit" % "2.5.7" % Test,

  // Akka Actors
  "com.typesafe.akka" %% "akka-actor" % "2.5.7",
  "com.typesafe.akka" %% "akka-testkit" % "2.5.7" % Test,

  // Akka Spray Json
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.0.11"
)

TaskKey[Unit]("httpServer") := (runMain in Compile).toTask(" ClientApi.HttpServer").value
