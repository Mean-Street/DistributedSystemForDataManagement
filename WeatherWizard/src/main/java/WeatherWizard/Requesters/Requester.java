package WeatherWizard.Requesters;

import WeatherWizard.Requests.OpenWeatherMapCurrentWeatherRequest;
import WeatherWizard.Responses.OpenWeatherMapCurrentWeatherResponse;
import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.http.javadsl.Http;
import akka.stream.Materializer;

public class Requester extends AbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    final private ActorRef openWeatherMapActorRef;
    final public String name = "requester";

    public Requester(Http http, Materializer materializer) {
        this.openWeatherMapActorRef = getContext().actorOf(OpenWeatherMapRequester.props(http, materializer), OpenWeatherMapRequester.name);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(OpenWeatherMapCurrentWeatherRequest.class, request -> {
                    openWeatherMapActorRef.forward(request, getContext());
                })
                .match(OpenWeatherMapCurrentWeatherResponse.class, response -> {
                    log.info(response.toString());
                })
                .build();
    }
}
