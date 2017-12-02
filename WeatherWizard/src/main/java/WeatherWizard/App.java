package WeatherWizard;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.http.javadsl.Http;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        String openWeatherMapKey = "7f484592e9396fae071a8f19acdc0e71";
        ActorSystem system = ActorSystem.create("WeatherWizard");
        Http httpGate = Http.get(system);
        Materializer materializer = ActorMaterializer.create(system);
        ActorRef openWeatherMap = system.actorOf(OpenWeatherMap.props(httpGate, materializer));

        openWeatherMap.tell(new OpenWeatherMapRequest(openWeatherMapKey), ActorRef.noSender());

        // system.terminate();
    }
}
