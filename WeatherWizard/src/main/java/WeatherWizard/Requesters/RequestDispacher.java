package WeatherWizard.Requesters;

import WeatherWizard.Requests.ApixuRequest;
import WeatherWizard.Requests.OpenWeatherMapRequest;
import WeatherWizard.Requests.WeatherBitRequest;
import WeatherWizard.Responses.ApixuResponse;
import WeatherWizard.Responses.OpenWeatherMapResponse;
import WeatherWizard.Responses.WeatherBitResponse;
import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.http.javadsl.Http;
import akka.stream.Materializer;

public class RequestDispacher extends AbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    final private ActorRef openWeatherMapActorRef;
    final private ActorRef weatherBitApiRef;
    final private ActorRef apixuApiRef;

    public static String name = "request-dispacher";

    public static Props props(Http http, Materializer materializer) {
        return Props.create(RequestDispacher.class, () -> new RequestDispacher(http, materializer));
    }

    private RequestDispacher(Http http, Materializer materializer) {
        this.openWeatherMapActorRef = getContext().actorOf(
                Requester.props(http, materializer, OpenWeatherMapRequest.class,
                                OpenWeatherMapResponse.class));
        this.weatherBitApiRef = getContext().actorOf(
                Requester.props(http, materializer, WeatherBitRequest.class,
                                WeatherBitResponse.class));
        this.apixuApiRef = getContext().actorOf(
                Requester.props(http, materializer, ApixuRequest.class,
                                ApixuResponse.class));
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(OpenWeatherMapRequest.class, request -> {
                    log.info("Forwarding a request to OpenWeatherMap");
                    openWeatherMapActorRef.forward(request, getContext());
                })
                .match(WeatherBitRequest.class, request -> {
                    log.info("Forwarding a request to WeatherBit");
                    weatherBitApiRef.forward(request, getContext());
                })
                .match(ApixuRequest.class, request -> {
                    log.info("Forwarding a request to Apixu");
                    apixuApiRef.forward(request, getContext());
                })
                .build();
    }
}
