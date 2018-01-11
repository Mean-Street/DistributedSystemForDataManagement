package WeatherFinder;

import WeatherFinder.Configurations.KafkaConfig;
import WeatherFinder.Requesters.RequestDispatcher;
import WeatherFinder.Requests.ApixuRequestTemperature;
import WeatherFinder.Requests.Location;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.http.javadsl.Http;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import scala.concurrent.duration.Duration;
import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

public class WeatherFinderApp
{

    public static void main(String[] args) throws IOException
    {
        ActorSystem system = ActorSystem.create("WeatherFinder");

        // http and materializer are needed to send and receive http requests
        Http http = Http.get(system);
        Materializer materializer = ActorMaterializer.create(system);

        String host = args[0];
        Integer port = Integer.parseInt(args[1]);
        String topic = args[2];
        KafkaConfig config = new KafkaConfig(host, port, topic);

        // ******** REQUEST WITH TIMER : each minute **********
        ActorRef ApixuTickActor = system.actorOf(RequestDispatcher.props(http, materializer, config));
        ApixuRequestTemperature request = new ApixuRequestTemperature(new Location("Grenoble", "fr"));
        system.scheduler().schedule(Duration.Zero(), Duration.create(60, TimeUnit.SECONDS),
                                    ApixuTickActor, request, system.dispatcher(), ActorRef.noSender());

        // ********** End TIMER **********
        
        // ********** TWEETS *************
        
        //const request = require('request');

        String CONSUMER_KEY = "AqE6FxhWmTpRMAE18JBjxldT9";
        String CONSUMER_SECRET = "OwCW5bbBDLNbQQfFW1gErKxonXoYWIzkXmyRPqw0WoajuUcrzU";
        String ACCESS_TOKEN = "950812402299342848-v3GoArUSUNFOrZhAAYU6AZgscK4uU32";
        String ACCESS_TOKEN_SECRET = "QJmCBDTAmbBP79AiVNrbCaj8aqURoTBRwJ32UMEr5P9YZ";
        
        double[][] locations = { {5.6901,45.157}, {5.7498,45.201} };

                
                
       
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(CONSUMER_KEY)
                .setOAuthConsumerSecret(CONSUMER_SECRET)
                .setOAuthAccessToken(ACCESS_TOKEN)
                .setOAuthAccessTokenSecret(ACCESS_TOKEN_SECRET);
       
        StatusListener listener = new StatusListener(){
            public void onStatus(Status status) {
                System.out.println(status);
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
        
        TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
//        twitterStream.setOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
        twitterStream.addListener(listener);
        FilterQuery filterQuery = new FilterQuery("botname");
//        //filterQuery.track();
        filterQuery.locations(locations);
        twitterStream.filter(filterQuery);

//        twitterStream.shutdown();
    }
}
