// https://github.com/apache/spark/blob/v2.2.0/examples/src/main/scala/org/apache/spark/examples/streaming/KafkaWordCount.scala

import kafka.serializer.StringDecoder

import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka._

import scala.util.parsing.json._
import com.datastax.spark.connector._
import com.datastax.driver.core.utils.UUIDs
import org.apache.spark.sql.cassandra._
import com.datastax.spark.connector.streaming._


object DataPreprocessing {
  def main(args: Array[String]) {
    if (args.length < 3) {
      System.exit(1)
    }

    val Array(brokers, topic, period) = args

    val sconf = new SparkConf()
    val ssc = new StreamingContext(sconf, Seconds(period.toLong))
    ssc.checkpoint("checkpoint")

    val topicsSet = Set(topic)
    val kafkaParams = Map[String, String]("metadata.broker.list" -> brokers)
    val messages = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](
      ssc, kafkaParams, topicsSet)

    /*
    messages.foreachRDD {
	rdd => rdd.foreach {
            msg => storeJson(msg._2, sc)
        }
    }
    */
    val rows = messages.map(msg => jsonToRow(msg._2))
    rows.saveToCassandra("sdtd", "temperatures", SomeColumns("id", "date", "temperature"))

    /*
    val lines = messages.map(_._2)
    val words = lines.flatMap(_.split(" "))
    val wordCounts = words.map(x => (x, 1L)).reduceByKey(_ + _)
    wordCounts.print()
    */

    ssc.start()
    ssc.awaitTermination()
  }

  def jsonToRow(msg: String) : (String, String, Double) = {
    val json = JSON.parseFull(msg).get.asInstanceOf[Map[String,Any]]
    val date = json("date")
    val temperature = json("temperature")
    return (com.datastax.driver.core.utils.UUIDs.timeBased().toString(), date.toString, temperature.asInstanceOf[Double])
  }
}
