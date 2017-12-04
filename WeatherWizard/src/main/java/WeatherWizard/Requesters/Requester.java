package WeatherWizard.Requesters;

import WeatherWizard.Requests.WeatherBitCurrentWeatherRequest;
import WeatherWizard.Responses.WeatherBitCurrentWeatherResponse;
import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.http.javadsl.Http;
import akka.stream.Materializer;

public class Requester extends AbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    final private ActorRef WeatherBitActorRef;
    final public String name = "requester";

    public Requester(Http http, Materializer materializer) {
        this.WeatherBitActorRef = getContext().actorOf(WeatherBitRequester.props(http, materializer), WeatherBitRequester.name);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(WeatherBitCurrentWeatherRequest.class, request -> {
                    WeatherBitActorRef.forward(request, getContext());
                })
                .match(WeatherBitCurrentWeatherResponse.class, response -> {
                    log.info(response.toString());
                })
                .build();
    }
}
