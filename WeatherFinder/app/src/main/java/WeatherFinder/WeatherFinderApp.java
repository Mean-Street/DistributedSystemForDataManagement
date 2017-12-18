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
    }
}
