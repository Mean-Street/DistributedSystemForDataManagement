package WeatherWizard;


import WeatherWizard.Configurations.KafkaConfig;
import WeatherWizard.Requesters.RequestDispatcher;
import WeatherWizard.Requests.ApixuRequestTemperature;

import WeatherWizard.Requests.Location;
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

public class WeatherWizardApp
{
    private static KafkaProducer<String, Double> initProducer(KafkaConfig config) {

        Properties props = new Properties();
        props.put("bootstrap.servers", config.toString());
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return new KafkaProducer<>(props);
    }

    public static void main(String[] args) throws IOException
    {
        ActorSystem system = ActorSystem.create("WeatherWizard");

        // http and materializer are needed to send and receive http requests
        Http http = Http.get(system);
        Materializer materializer = ActorMaterializer.create(system);

        Integer[] host = {127, 0, 0, 0};
        KafkaConfig config = new KafkaConfig(host, 5555);
        KafkaProducer<String, Double> producer = initProducer(config);

        // ******** REQUEST WITH TIMER : each minute **********
        ActorRef ApixuTickActor = system.actorOf(RequestDispatcher.props(http, materializer, producer));
        ApixuRequestTemperature request = new ApixuRequestTemperature(new Location("Grenoble", "fr"));
        system.scheduler().schedule(Duration.Zero(), Duration.create(60, TimeUnit.SECONDS),
                                    ApixuTickActor, request, system.dispatcher(), ActorRef.noSender());

        // ********** End TIMER **********
    }
}
