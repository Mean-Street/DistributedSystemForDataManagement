package WeatherWizard;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.http.javadsl.Http;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;

public class App
{
    public static void main( String[] args )
    {
        String openWeatherMapKey = "7f484592e9396fae071a8f19acdc0e71";
        ActorSystem system = ActorSystem.create("WeatherWizard");

        // httpGate and materializer are needed to send and receive http requests
        Http httpGate = Http.get(system);
        Materializer materializer = ActorMaterializer.create(system);

        ActorRef openWeatherMap = system.actorOf(OpenWeatherMap.props(httpGate, materializer), OpenWeatherMap.name);

        openWeatherMap.tell(new OpenWeatherMapCurrentWeatherRequest(openWeatherMapKey, "Roma", "it"),
                            ActorRef.noSender());

        // system.terminate();
    }
}
