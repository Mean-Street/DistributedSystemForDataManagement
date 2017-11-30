
import org.apache.spark.SparkContext
import org.apache.spark.SparkConf

import com.datastax.spark.connector._
import org.apache.spark.sql.cassandra._

object Main {
  def main(args: Array[String]) {

    val conf = new SparkConf(true)
      .setAppName("Main").setMaster("local[2]")
      .set("spark.cassandra.connection.host", "localhost")
      .set("spark.cassandra.auth.username", "cassandra")            
      .set("spark.cassandra.auth.password", "cassandra")
    val sc = new SparkContext(conf)

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

    // # Load RDD from Cassandra
    // (created with cassandra/createData.cql)
    val rdd2 = sc.cassandraTable("sdtd", "weatherFromCQL")
    rdd2.collect().foreach(println)
  }

}
