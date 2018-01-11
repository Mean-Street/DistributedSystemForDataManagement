package WeatherFinder.Requesters;


import WeatherFinder.Configurations.KafkaConfig;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.http.javadsl.Http;
import akka.stream.Materializer;


import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;

import java.util.Properties;
import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.json.DataObjectFactory;

public class RequesterTwitter extends AbstractActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    private final Http httpGate;
    private final Materializer materializer;
    private final Producer<String, String> producer;
    private final KafkaConfig config;
    private StatusListener listener;

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
                            System.out.println(status);
                            String json = DataObjectFactory.getRawJSON(status);         
                            System.out.println(json);
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
        log.info("createReceive");
        return receiveBuilder()

                .matchEquals("start_twitter", request -> {
//                    startTwitter(listener);
                    
//                    requestCurrentWeather(request).thenAccept(resp -> {
//                        try {
//                            String responseSerialized = new ObjectMapper().writeValueAsString(resp);
//                            ProducerRecord<String, String> newRecord = new ProducerRecord<>(
//                                    config.getTopic(), request.getLocation().getCity(), responseSerialized);
//                            log.info("Got a response from: " + request.getLocation().getCity());
//                            log.info("Writing response on: " + config.getTopic());
//                            log.info("response: " + newRecord);
//                            producer.send(newRecord);
//                        } catch (JsonProcessingException e) {
//                            log.error("Response encountered an error during serialization");
//                        }
//                    });
                })
                .build();
    }
    
    private void startTwitter(StatusListener listener) {
                    String CONSUMER_KEY = "AqE6FxhWmTpRMAE18JBjxldT9";
                    String CONSUMER_SECRET = "OwCW5bbBDLNbQQfFW1gErKxonXoYWIzkXmyRPqw0WoajuUcrzU";
                    String ACCESS_TOKEN = "950812402299342848-v3GoArUSUNFOrZhAAYU6AZgscK4uU32";
                    String ACCESS_TOKEN_SECRET = "QJmCBDTAmbBP79AiVNrbCaj8aqURoTBRwJ32UMEr5P9YZ";

                    double[][] grenoble = { {5.6901,45.157}, {5.7498,45.201} };
                    double[][] new_york = { {-74,40}, {-73,41} };




                    ConfigurationBuilder cb = new ConfigurationBuilder();
                    cb.setDebugEnabled(true)
                            .setOAuthConsumerKey(CONSUMER_KEY)
                            .setOAuthConsumerSecret(CONSUMER_SECRET)
                            .setOAuthAccessToken(ACCESS_TOKEN)
                            .setOAuthAccessTokenSecret(ACCESS_TOKEN_SECRET);

                    TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
            //        twitterStream.setOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
                    twitterStream.addListener(listener);
                    FilterQuery filterQuery = new FilterQuery("botname");
            //        //filterQuery.track();
                    filterQuery.locations(new_york);
                    twitterStream.filter(filterQuery);
    }

//    private CompletionStage<ResponseClass> requestCurrentWeather(Status status) {
//        Unmarshaller<HttpEntity, ResponseClass> decoder = Jackson.unmarshaller(responseClass);
//        return status.thenCompose(resp -> decoder.unmarshal(resp.entity(), this.materializer));
//    }
}
