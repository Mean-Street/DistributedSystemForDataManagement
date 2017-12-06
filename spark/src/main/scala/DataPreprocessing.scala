// https://github.com/apache/spark/blob/v2.2.0/examples/src/main/scala/org/apache/spark/examples/streaming/KafkaWordCount.scala

import kafka.serializer.StringDecoder

import org.apache.spark.SparkConf
import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka._

object DataPreprocessing {
  def main(args: Array[String]) {
    if (args.length < 5) {
      System.exit(1)
    }

    val Array(zkQuorum, group, topics, numThreads, period) = args

    val ssc = new StreamingContext(new SparkConf(), Seconds(period.toLong))
    ssc.checkpoint("checkpoint")

    val topicMap = topics.split(",").map((_, numThreads.toInt)).toMap
    val lines = KafkaUtils.createStream(ssc, zkQuorum, group, topicMap).map(_._2)
    val words = lines.flatMap(_.split(" "))
    val wordCounts = words.map(x => (x, 1L))
      .reduceByKeyAndWindow(_ + _, _ - _, Minutes(10), Seconds(2), 2)
    wordCounts.print()

    ssc.start()
    ssc.awaitTermination()
  }
}
