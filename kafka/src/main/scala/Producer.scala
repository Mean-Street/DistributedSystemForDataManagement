import java.util.Properties

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

object Producer extends App {
  val N_MSG = 2
  val topic = args(0)
  val brokers = args(1)
  val props = new Properties()
  props.put("bootstrap.servers", brokers)
  props.put("client.id", "Producer")
  props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")


  val producer = new KafkaProducer[String, String](props)
  for ( i <- 1 to N_MSG ) {
    println(i)
    val data = new ProducerRecord[String, String](topic, Integer.toString(i), Integer.toString(i))
    producer.send(data)
  }
  println("end")
  producer.close()
}
