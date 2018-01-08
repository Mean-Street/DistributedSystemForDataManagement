import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import com.datastax.spark.connector._
import org.apache.spark.sql.cassandra._


object TwitterPreprocessing {
  def main(args: Array[String]) {
    if (args.length < 2) {
      System.exit(1)
    }

    val sconf = new SparkConf()
    val sc = new SparkContext(sconf)

    val rdd = sc.cassandraTable("sdtd", "tweet")
    rdd.collect().foreach(println)
  }
}
