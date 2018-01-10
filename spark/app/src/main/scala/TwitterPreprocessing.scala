import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import com.datastax.spark.connector._
import org.apache.spark.sql.cassandra._
import utils.SentimentAnalysisUtils._


object TwitterPreprocessing {
  def main(args: Array[String]) {

    val sconf = new SparkConf()
    val sc = new SparkContext(sconf)

    // TODO: load tweets from Kafka
    val tweets = sc.parallelize(Array(
    "",
    "Mark is a really good football player",
    "Mark is a good football player",
    "Mark is an OK football player",
    "Mark is a bad football player",
    "Mark is a really bad football player",
    "Pizzas are amazing",
    "Amazing !",
    "The grass is green",
    "Horrible", 
    "That TV show was perfect!"
    ))

    val sentiments = tweets.map(tweet => (tweet, detectSentiment(tweet)))
    sentiments.foreach(println)
  }
}
