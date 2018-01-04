import org.apache.spark.SparkContext
import org.apache.spark.SparkConf

import com.datastax.spark.connector._
import com.datastax.driver.core.utils.UUIDs
import org.apache.spark.sql.cassandra._

object DataCleaning {
  def main(args: Array[String]) {

    val sc = new SparkContext(new SparkConf())

    // val lines = sc.textFile("data.txt")
    // val lineLengths = lines.map(s => s.length)
    // val totalLength = lineLengths.reduce((a, b) => a + b)
    // println("OUTPUT:", totalLength)

    // # Store RDD in Cassandra (to be continued)
    // val rdd1 = sc.parallelize(Seq(
    //   (1, "New York", 22),
    //   (2, "Paris", 14),
    //   (3, "Tokyo", 11),
    //   (4, "Londres", 14),
    //   (5, "Dubai", 31),
    //   (6, "Moscou", 6)
    //   )
    // )
    // rdd1.saveToCassandra("sdtd", "weatherFromJSON", SomeColumns("id", "city", "temperature"))


    println("OK 1");
    val c1 = sc.parallelize(Seq((UUIDs.timeBased(), "lundi", 13.3)))
    c1.saveToCassandra("sdtd", "temperatures", SomeColumns("id", "date", "temperature"))
    val c2 = sc.parallelize(Seq((UUIDs.timeBased(), "mardi", -3.8)))
    c2.saveToCassandra("sdtd", "temperatures", SomeColumns("id", "date", "temperature"))
    println("OK 2");
    /*
    val c3 = sc.parallelize(Seq(("mercredi", 1.32)))
    c3.saveToCassandra("sdtd", "temperatures", SomeColumns("date", "temperature"))
    println("OK 3");
    val c4 = sc.parallelize(Seq(("jeudi", 16.411)))
    c4.saveToCassandra("sdtd", "temperatures", SomeColumns("date", "temperature"))
    println("OK 4");
    val c5 = sc.parallelize(Seq(("vendredi", 6)))
    c5.saveToCassandra("sdtd", "temperatures", SomeColumns("date", "temperature"))
    println("OK 5");
    val c6 = sc.parallelize(Seq(("samedi", 0)))
    c6.saveToCassandra("sdtd", "temperatures", SomeColumns("date", "temperature"))
    println("OK 6");
    val c7 = sc.parallelize(Seq(("dimanche", 4.08)))
    c7.saveToCassandra("sdtd", "temperatures", SomeColumns("date", "temperature"))
    println("OK 7");
    */


    // # Load RDD from Cassandra
    // (created with cassandra/createData.cql)
    // val rdd2 = sc.cassandraTable("sdtd", "weatherfromcql")
    // rdd2.collect().foreach(println)
  }
}

