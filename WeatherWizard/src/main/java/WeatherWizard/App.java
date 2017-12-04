package WeatherWizard;

import WeatherWizard.Requesters.ApixuRequester;
import WeatherWizard.Requesters.WeatherBitRequester;
import WeatherWizard.Requesters.OpenWeatherMapRequester;
import WeatherWizard.Requests.ApixuCurrentWeatherRequest;
import WeatherWizard.Requests.WeatherBitCurrentWeatherRequest;
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

        // launch actors
//        ActorRef ApixuApiRef = system.actorOf(ApixuRequester.props(httpGate, materializer), ApixuRequester.name);
//        
//        ApixuApiRef.tell(new ApixuCurrentWeatherRequest("Grenoble"),
//                                  ActorRef.noSender());
//
//        ActorRef openWeatherMapApiRef = system.actorOf(OpenWeatherMapRequester.props(httpGate, materializer), OpenWeatherMapRequester.name);
//
//        openWeatherMapApiRef.tell(new OpenWeatherMapCurrentWeatherRequest("Grenoble", "fr"),
//                                  ActorRef.noSender());

        ActorRef WeatherBitApiRef = system.actorOf(WeatherBitRequester.props(httpGate, materializer), WeatherBitRequester.name);

        WeatherBitApiRef.tell(new WeatherBitCurrentWeatherRequest("Grenoble"),
                                  ActorRef.noSender());

        // system.terminate();
    }
}
