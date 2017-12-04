package WeatherWizard;

import WeatherWizard.Requests.Location;
import WeatherWizard.Requests.OpenWeatherMapCurrentWeatherRequest;
import akka.actor.ActorRef;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.Route;

public class HttpServer extends AllDirectives {

    private final ActorRef requester;

    public HttpServer(ActorRef requester) {
        this.requester = requester;
    }

    public Route createRoute() {
        return route(
            post(() ->
                path("temperature", () ->
                    entity(Jackson.unmarshaller(Location.class), location -> {
                        requester.tell(new OpenWeatherMapCurrentWeatherRequest(location), ActorRef.noSender());
                        return complete("Got a request for: " + location.toString());
                    })
                )
            )
        );
    }
}
