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
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.kafka.clients.producer.KafkaProducer;
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

    

    private static KafkaProducer<String, String> initProducer(KafkaConfig config) {

        Properties props = new Properties();
        props.put("bootstrap.servers", config.getBroker());
        props.put("client.id", "Producer");
        props.put("max.block.ms", "5000");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return new KafkaProducer<>(props);
    }
    
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
        
        // ******** WeatherBit ********
//        ActorRef WeatherBitTickActor = system.actorOf(RequestDispatcher.props(http, materializer, config));
//        WeatherBitRequestTemperature WBRequest = new WeatherBitRequestTemperature(new Location("new+york", "USA"));
//        system.scheduler().schedule(Duration.Zero(), Duration.create(60, TimeUnit.SECONDS),
//                                    WeatherBitTickActor, WBRequest, system.dispatcher(), ActorRef.noSender());
        
        // ******** OpenWeatherMap ********
        
//        ActorRef OpenWeatherMapTickActor = system.actorOf(RequestDispatcher.props(http, materializer, config));
//        OpenWeatherMapRequestTemperature OWMRequest = new OpenWeatherMapRequestTemperature(new Location("new+york", "USA"));
//        system.scheduler().schedule(Duration.Zero(), Duration.create(60, TimeUnit.SECONDS),
//                                    OpenWeatherMapTickActor, OWMRequest, system.dispatcher(), ActorRef.noSender());
        
        // ******** Apixu ********
        
        ActorRef actor = system.actorOf(RequestDispatcher.props(http, materializer, config));
//        ApixuRequestTemperature apixuRequest = new ApixuRequestTemperature(new Location("new_york", "USA"));
//        system.scheduler().schedule(Duration.Zero(), Duration.create(60, TimeUnit.SECONDS),
//                                    actor, apixuRequest, system.dispatcher(), ActorRef.noSender());
        
        actor.tell("start_twitter", ActorRef.noSender());
        

        // ********** End TIMER **********
        
        // ********** TWEETS *************
        
        //const request = require('request');
                    

//        twitterStream.shutdown();
    }
}
