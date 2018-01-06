//import sbt.Keys._
import sbt._


name := "akka-http-helloworld"

version := "1.0"

scalaVersion := "2.11.5"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http-experimental" % "1.0",
  "com.typesafe.akka" %% "akka-http-spray-json-experimental" % "1.0",
  "com.typesafe.akka" %% "akka-http-testkit-experimental" % "1.0",

  "org.apache.spark" % "spark-core_2.11" % "2.1.0",
  "com.datastax.spark" %% "spark-cassandra-connector" % "2.0.2",

  "org.apache.spark" %% "spark-sql" % "2.2.0",

  "org.json4s" %% "json4s-native" % "3.2.11"
)