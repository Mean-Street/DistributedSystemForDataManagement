name := "SDTD"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
    // https://mvnrepository.com/artifact/org.apache.spark
    "org.apache.spark" %% "spark-sql" % "2.2.0" % "provided",
    "org.apache.spark" %% "spark-streaming" % "2.2.0" % "provided",
    "org.apache.spark" %% "spark-streaming-kafka-0-8" % "2.2.0",
    "com.datastax.spark" %% "spark-cassandra-connector" % "2.0.6",
	"edu.stanford.nlp" % "stanford-corenlp" % "3.5.1",
	"edu.stanford.nlp" % "stanford-corenlp" % "3.5.1" classifier "models",

  // Akka HTTP
  //"com.typesafe.akka" %% "akka-http" % "10.0.11",
  //"com.typesafe.akka" %% "akka-http-testkit" % "10.0.11" % Test,

  // Akka Stream
  //"com.typesafe.akka" %% "akka-stream" % "2.5.7",
  //"com.typesafe.akka" %% "akka-stream-testkit" % "2.5.7" % Test,

  // Akka Actors
  //"com.typesafe.akka" %% "akka-actor" % "2.5.7",
  //"com.typesafe.akka" %% "akka-testkit" % "2.5.7" % Test,

  // Akka Spray Json
  //"com.typesafe.akka" %% "akka-http-spray-json" % "10.0.11"
)

//TaskKey[Unit]("httpServer") := (runMain in Compile).toTask(" ClientApi.HttpServer").value

// See https://stackoverflow.com/a/27715280
assemblyMergeStrategy in assembly := {
  case m if m.toLowerCase.endsWith("manifest.mf")          => MergeStrategy.discard
  case m if m.toLowerCase.matches("meta-inf.*\\.sf$")      => MergeStrategy.discard
  case "log4j.properties"                                  => MergeStrategy.discard
  case m if m.toLowerCase.startsWith("meta-inf/services/") => MergeStrategy.filterDistinctLines
  case "reference.conf"                                    => MergeStrategy.concat
  case _                                                   => MergeStrategy.first
}
