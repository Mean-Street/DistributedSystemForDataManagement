import kafka.serializer.StringDecoder

import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka._

import scala.util.parsing.json._
import com.datastax.spark.connector._
import com.datastax.driver.core.utils.UUIDs
import org.apache.spark.sql.cassandra._
import utils.SentimentAnalysisUtils._
import com.datastax.spark.connector.streaming._


object TwitterPreprocessing {
  def main(args: Array[String]) {
    if (args.length < 3) {
      println("Not enough arguments given.")
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
    val rows = messages.map(msg => jsonToRow(msg._2))
    //rows.foreachRDD { rdd => if (!rdd.isEmpty) rdd.foreach(println) }
    rows.saveToCassandra("sdtd", "tweet", SomeColumns("id", "date", "sentiment", "tweet"))

    ssc.start()
    ssc.awaitTermination()
  }

  def jsonToRow(msg: String) : (String, String, String, String) = {
    //println(JSON.parseFull(msg))
    val json = JSON.parseFull(msg).get.asInstanceOf[Map[String,Any]]
    val id = json("id").toString
    val tweet = json("text").toString
    val sentiment = detectSentiment(tweet).toString
    val date = json("created_at").toString
    return (id, date, sentiment, tweet)
  }
}
