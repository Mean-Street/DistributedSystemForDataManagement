import java.util.Properties

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import com.datastax.spark.connector._
import org.apache.spark.sql.cassandra._

//Usage WeatherProducer "topics" "@broker1,@broker2"
    /*
object WeatherProducer extends App {
    val topic = args(0)
    val brokers = args(1)
    val props = new Properties()
    props.put("bootstrap.servers", brokers)
    props.put("client.id", "Producer")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

    val producer = new KafkaProducer[String, Double](props)
    
    //Get Data from BD
    val sconf = new SparkConf()
    val sc = new SparkContext(sconf)

    //TODO
    val firstRow = sc.cassandraTable("sdtd", "temperatures").first()

    val date : String = "date"
    val temp : Double = 5.2
    //val date : String = firstRow.get(0); //.select("date")
    //val temp : Double = firstRow.get(1);

    //Send to brokers
    val data = new ProducerRecord[String, Double](topic, date, temp)
    producer.send(data)

    producer.close()
  }
}
    */
