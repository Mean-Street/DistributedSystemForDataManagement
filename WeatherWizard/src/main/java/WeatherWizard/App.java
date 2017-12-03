package WeatherWizard;

import WeatherWizard.Requesters.OpenWeatherMapRequester;
import WeatherWizard.Requests.OpenWeatherMapCurrentWeatherRequest;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.http.javadsl.Http;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;

public class App
{
    public App() {
    }

    public static void main(String[] args )
    {
        ActorSystem system = ActorSystem.create("WeatherWizard");

        // httpGate and materializer are needed to send and receive http requests
        Http httpGate = Http.get(system);
        Materializer materializer = ActorMaterializer.create(system);

        ActorRef openWeatherMapApiRef = system.actorOf(OpenWeatherMapRequester.props(httpGate, materializer), OpenWeatherMapRequester.name);

        openWeatherMapApiRef.tell(new OpenWeatherMapCurrentWeatherRequest("Roma", "it"),
                                  ActorRef.noSender());

        // system.terminate();
    }
}
