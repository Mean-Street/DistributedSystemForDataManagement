import java.util.concurrent._
import java.util.{Collections, Properties}

import kafka.consumer.KafkaStream
import kafka.utils.Logging
import org.apache.kafka.clients.consumer.{ConsumerConfig, KafkaConsumer}

import scala.collection.JavaConversions._

object Consumer extends App {
  /*
  val topic = args(1)
  val brokers = args(2)

  Properties props = new Properties();
  props.put("bootstrap.servers", brokers);
  props.put("group.id", "group-test");
  //props.put("enable.auto.commit", "true");
  //props.put("auto.commit.interval.ms", "1000");
  props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
  props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

  val consumer = new KafkaConsumer[String, String](props)
  consumer.subscribe(Arrays.asList("foo", "bar"));
  while (true) {
    ConsumerRecords<String, String> records = consumer.poll(100);
    for (ConsumerRecord<String, String> record : records)
      println("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value())
  }
  */
}
