package WeatherWizard;

import WeatherWizard.Requests.Location;
import WeatherWizard.Requests.OpenWeatherMapRequestTemperature;
import akka.actor.ActorRef;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.Route;

class HttpServer extends AllDirectives {

    private final ActorRef requestDispacher;

    HttpServer(ActorRef requestDispacher) {
        this.requestDispacher = requestDispacher;
    }

    Route createRoute() {
        return route(
            post(() ->
                pathPrefix("temperature", () ->
                    path("openWeatherMap", () ->
                        entity(Jackson.unmarshaller(Location.class), location -> {
                            requestDispacher.tell(new OpenWeatherMapRequestTemperature(location), ActorRef.noSender());
                            return complete("Got a request for: " + location.toString());
                        })
                    )
                )
            )
        );
    }
}
