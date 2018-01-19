package WeatherFinder.Requesters;


import WeatherFinder.Configurations.KafkaConfig;
import WeatherFinder.Responses.TwitterResponse;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.http.javadsl.Http;
import akka.stream.Materializer;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.kafka.clients.producer.ProducerRecord;

public class RequesterTwitter extends AbstractActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    private final Http httpGate;
    private final Materializer materializer;
    private final Producer<String, String> producer;
    private final KafkaConfig config;

    private static KafkaProducer<String, String> initProducer(KafkaConfig config) {

        Properties props = new Properties();
        props.put("bootstrap.servers", config.getBroker());
        props.put("client.id", "Producer");
        props.put("max.block.ms", "5000");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return new KafkaProducer<>(props);
    }

    private RequesterTwitter(Http httpGate, Materializer materializer, KafkaConfig config) {
        this.httpGate = httpGate;
        this.materializer = materializer;
        this.config = config;
        this.producer = initProducer(config);
    };

    static Props props( Http httpGate, Materializer materializer, KafkaConfig config) {
        return Props.create(RequesterTwitter.class, () -> new RequesterTwitter(httpGate, materializer, config));
    }

    @Override
    public Receive createReceive() {
//        log.info("createReceive");
        return receiveBuilder()

                .match(String.class, json -> {
                    tweetProcessing(json);
                })
                .build();
    }
    
    private void benchmarkingNotify(int cpt) {
        //create date
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formatDateTime = "{date:"+date.format(formatter)+",cpt:"+cpt+"}";
        //create kafka producer
        ProducerRecord<String, String> benchmarkingRecord = new ProducerRecord<>(config.getTopic(), formatDateTime);
        log.info("response: " + benchmarkingRecord);
        // sending to kafka
        producer.send(benchmarkingRecord);
        System.out.println("Kafka notified at " + benchmarkingRecord);
    }
    
    private void tweetProcessing(String json) {
        try {
            // Handle Json Object
            int cpt = Integer.parseInt(json.substring(json.lastIndexOf("/") + 1));
//            System.out.println("to Kafka : " + cpt);
            json = json.substring(0, json.lastIndexOf("/"));
            TwitterResponse response = parseJson(json);
            //Send json to Kafka
            String responseSerialized = new ObjectMapper().writeValueAsString(response);
            ProducerRecord<String, String> newRecord = new ProducerRecord<>(config.getTopic(), responseSerialized);
            log.info("response: " + newRecord);
            producer.send(newRecord);
                                
            // benchmarking
            if (cpt % 100 == 0) {
//                benchmarkingNotify(cpt);
            }
        } catch (IOException ex) {
            Logger.getLogger(RequesterTwitter.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    private TwitterResponse parseJson(String json) throws IOException {
        TwitterResponse response = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .readerFor(TwitterResponse.class)
                .readValue(json);
        return response;
    }
}
