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
import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterObjectFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

public class RequesterTwitter extends AbstractActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    private final Http httpGate;
    private final Materializer materializer;
    private final Producer<String, String> producer;
    private final KafkaConfig config;
    private StatusListener listener;
    private int cpt = 0;
    private TwitterStream twitterStream;

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
        this.listener = new StatusListener(){
                        public void onStatus(Status status) {
//                            try {
                                // Handle Json Object
//                                String json = TwitterObjectFactory.getRawJSON(status);
//                                TwitterResponse response = parseJson(json);
//                                //Send json to Kafka
//                                String responseSerialized = new ObjectMapper().writeValueAsString(response);
//                                ProducerRecord<String, String> newRecord = new ProducerRecord<>(config.getTopic(), responseSerialized);
//                                log.info("response: " + newRecord);
//                                producer.send(newRecord);
                                
                                // benchmarking
                                cpt += 1;
                                System.out.println(cpt);
                                if (cpt >= 100) {
                                    benchmarkingNotify();
                                }
//                            } catch (IOException ex) {
//                                Logger.getLogger(RequesterTwitter.class.getName()).log(Level.SEVERE, null, ex);
//                            }
                        }
                        public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
                            System.out.println("onDeletionNotice");
                        }
                        public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
                            System.out.println("onTrackLimitationNotice");
                        }
                        public void onException(Exception ex) {
                            System.out.println("onException");
                            ex.printStackTrace();
                        }
                        public void onScrubGeo(long l, long l1) {
                            System.out.println("onScrubGeo");
                        }
                        public void onStallWarning(StallWarning sw) {
                            System.out.println("onStallWarning");
                        }
        };
    };

    static Props props( Http httpGate, Materializer materializer, KafkaConfig config) {
        return Props.create(RequesterTwitter.class, () -> new RequesterTwitter(httpGate, materializer, config));
    }

    @Override
    public Receive createReceive() {
//        log.info("createReceive");
        return receiveBuilder()

                .matchEquals("start_twitter", request -> {
                    startTwitter(listener);
                })

//                .matchEquals("stop_twitter", request -> {
//                    stopTwitter();
//                })
                .build();
    }
    
    private void benchmarkingNotify() {
        //create date
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formatDateTime = date.format(formatter);
        //create kafka producer
        ProducerRecord<String, String> benchmarkingRecord = new ProducerRecord<>(config.getTopic(), formatDateTime);
        log.info("response: " + benchmarkingRecord);
        // sending to kafka
        producer.send(benchmarkingRecord);
        System.out.println("Kafka notified at " + formatDateTime);
    }
    
//    private void stopTwitter() {
//        System.out.println("stopTwitter cpt : " + cpt);
//        if (cpt > 10) {
//            twitterStream.cleanUp();
//            twitterStream.shutdown();
//            System.out.println("Twitter Stream stoped");
//        }
//    }
    
    private void startTwitter(StatusListener listener) {
        
                    /* APPLI quad*/
                    String CONSUMER_KEY = "J9gIWL5eoJngkNTV9FW0vorbU";
                    String CONSUMER_SECRET = "TBKlCMItHad8dwe5w1eTTvxBY1Q5wXirzTdc2qyNW9gngjeeT6";
                    String ACCESS_TOKEN = "950812402299342848-XFWUrLgbote4C441mMOVJPGUBalSwKD";
                    String ACCESS_TOKEN_SECRET = "gdos2Seje0B76J4nLU0ftL2zXMSXYFgy23lYpo20SI3Mm";
                    
                    /* APPLI first*/
//                    String CONSUMER_KEY = "v4BwxiEOnxdkoYaPBxJGpYOlP";
//                    String CONSUMER_SECRET = "zxIXSfZErARTYm10j2yK58LeOjDDR30MPyYgijb8qpB2xILq0H";
//                    String ACCESS_TOKEN = "950812402299342848-pPqJnpXdXCB13X1DU4sigXmdIDP7RLA";
//                    String ACCESS_TOKEN_SECRET = "viDhaLyQtsK86fsGIneacXrJAxogax9c6JFZvIrskQVlq";
        
                    /* APPLI PrePresentation*/
//                    String CONSUMER_KEY = "Z0RVHxgllP5TFbGqyZjwG45Br";
//                    String CONSUMER_SECRET = "0ebIeqaX7KraVWvPjy6ixxMgIFy7yIscWHO1S7kdojrdOfXECe";
//                    String ACCESS_TOKEN = "950812402299342848-svOgXuXmf8rPQKseVCE8qJSQISThKUY";
//                    String ACCESS_TOKEN_SECRET = "aM8nMNWSDdMyOhgxoNhxa6kz4LURtlv8SnGjkUJuMTRIg";

                    double[][] grenoble = { {5.6901,45.157}, {5.7498,45.201} };
                    double[][] new_york = { {-74,40}, {-73,41} };
                    double[][] slice_of_usa = { {-124,35}, {-73,41} };

                    ConfigurationBuilder cb = new ConfigurationBuilder();
                    cb.setDebugEnabled(true)
                            .setOAuthConsumerKey(CONSUMER_KEY)
                            .setOAuthConsumerSecret(CONSUMER_SECRET)
                            .setOAuthAccessToken(ACCESS_TOKEN)
                            .setOAuthAccessTokenSecret(ACCESS_TOKEN_SECRET)
                            .setJSONStoreEnabled(true);

                    twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
            //        twitterStream.setOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
                    twitterStream.addListener(listener);
                    FilterQuery filterQuery = new FilterQuery("botname");
            //        //filterQuery.track();
                    filterQuery.locations(slice_of_usa);
                    twitterStream.filter(filterQuery);
    }

    private TwitterResponse parseJson(String json) throws IOException {
        TwitterResponse response = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .readerFor(TwitterResponse.class)
                .readValue(json);
        return response;
    }
}
