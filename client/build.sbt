//import sbt.Keys._
import sbt._


name := "client"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  // Akka Actors
  "com.typesafe.akka" %% "akka-actor" % "2.5.8",

  // Akka Http
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.0-RC1",
  "com.typesafe.akka" %% "akka-http" % "10.1.0-RC1",

  // Akka Streams
  "com.typesafe.akka" %% "akka-stream" % "2.5.8",

  // Spark
  "org.apache.spark" %% "spark-core" % "2.2.1",
  "org.apache.spark" %% "spark-sql" % "2.2.1",
  "org.apache.spark" %% "spark-mllib" % "2.2.1",

  // Spark - Cassandra connector
  "com.datastax.spark" %% "spark-cassandra-connector" % "2.0.6"
)

// See https://stackoverflow.com/a/27715280
assemblyMergeStrategy in assembly := {
  case m if m.toLowerCase.endsWith("manifest.mf")          => MergeStrategy.discard
  case m if m.toLowerCase.matches("meta-inf.*\\.sf$")      => MergeStrategy.discard
  case "log4j.properties"                                  => MergeStrategy.discard
  case m if m.toLowerCase.startsWith("meta-inf/services/") => MergeStrategy.filterDistinctLines
  case "reference.conf"                                    => MergeStrategy.concat
  case _                                                   => MergeStrategy.first
}
