import java.util.Properties

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

//Usage WeatherProducer "topics" "@broker1,@broker2"
object WeatherProducer extends App {
  def main(args: Array[String]) {
    val topic = args(0)
    val brokers = args(1)
    val props = new Properties()
    props.put("bootstrap.servers", brokers)
    props.put("client.id", "Producer")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

    val producer = new KafkaProducer[String, Double](props)
    
    //TODO
    //Get Data from BD
    val date : String = "07/12/17";
    val temp : Double = 5.2;

    //Send to brokers
    val data = new ProducerRecord[String, Double](topic, date, temp)
    producer.send(data)

    producer.close()
  }
}