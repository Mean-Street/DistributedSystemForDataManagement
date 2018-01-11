package WeatherFinder;

import WeatherFinder.Configurations.KafkaConfig;
import WeatherFinder.Requesters.RequestDispatcher;
import WeatherFinder.Requests.ApixuRequestTemperature;
import WeatherFinder.Requests.Location;
import WeatherFinder.Requests.OpenWeatherMapRequestTemperature;
import WeatherFinder.Requests.WeatherBitRequestTemperature;

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

        // creation of the actor in order to retrieve weather and tweets
        ActorRef actor = system.actorOf(RequestDispatcher.props(http, materializer, config));

        
        // ******** REQUEST WITH TIMER : each minute **********
        
        // ******** WeatherBit ********
        WeatherBitRequestTemperature WBRequest = new WeatherBitRequestTemperature(new Location("new+york", "USA"));
        system.scheduler().schedule(Duration.Zero(), Duration.create(60, TimeUnit.SECONDS),
                                    actor, WBRequest, system.dispatcher(), ActorRef.noSender());
        
        // ******** OpenWeatherMap ********
        
        OpenWeatherMapRequestTemperature OWMRequest = new OpenWeatherMapRequestTemperature(new Location("new+york", "USA"));
        system.scheduler().schedule(Duration.Zero(), Duration.create(60, TimeUnit.SECONDS),
                                    actor, OWMRequest, system.dispatcher(), ActorRef.noSender());
        
        // ******** Apixu ********
        
        ApixuRequestTemperature apixuRequest = new ApixuRequestTemperature(new Location("new_york", "USA"));
        system.scheduler().schedule(Duration.Zero(), Duration.create(60, TimeUnit.SECONDS),
                                    actor, apixuRequest, system.dispatcher(), ActorRef.noSender());

        
        // ******** TWEETS STREAMING **********
        
        // trigger the tweet streaming (in new york)
        actor.tell("start_twitter", ActorRef.noSender());
    }
}
