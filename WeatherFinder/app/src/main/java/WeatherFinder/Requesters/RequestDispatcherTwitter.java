package WeatherFinder.Requesters;

import WeatherFinder.Configurations.KafkaConfig;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.http.javadsl.Http;
import akka.routing.RoundRobinPool;
import akka.stream.Materializer;
import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterObjectFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

public class RequestDispatcherTwitter extends AbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    private final ActorRef twitterRef;
    private TwitterStream twitterStream;
    private StatusListener listener;
    private int cpt = 0;

    public final static String name = "request-dispatcher";

    public static Props props(Http http, Materializer materializer, KafkaConfig config) {
        return Props.create(RequestDispatcherTwitter.class, () -> new RequestDispatcherTwitter(http, materializer, config));
    }

    private RequestDispatcherTwitter(Http http, Materializer materializer, KafkaConfig config) {

        this.twitterRef = getContext().actorOf(new RoundRobinPool(4).props(RequesterTwitter.props(http, materializer, config)));
        this.listener = new StatusListener(){
                        public void onStatus(Status status) {
                            cpt += 1;
//                            System.out.println("to process : " + cpt);
                            String json = TwitterObjectFactory.getRawJSON(status);
                            twitterRef.forward(json+"/"+cpt, getContext());
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
    }

    @Override
    public AbstractActor.Receive createReceive() {
        return receiveBuilder()
                .matchEquals("start_twitter", s -> {
                    startTwitter(listener);
                })
                .build();
    }
    
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
                    filterQuery.locations(new_york);
                    twitterStream.filter(filterQuery);
    }
    
}

