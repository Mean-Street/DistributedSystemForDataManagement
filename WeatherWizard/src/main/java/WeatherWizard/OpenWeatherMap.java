package WeatherWizard;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import akka.http.javadsl.Http;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.model.HttpEntity;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.unmarshalling.Unmarshaller;
import akka.stream.Materializer;

import java.util.concurrent.CompletionStage;


/**
 * This class implements the methods needed to communicate with OpenWeatherMap's API.
 * When a correct request is sent to this actor, it will connect to OpenWeatherMap's
 * servers and process the response, returning it to the sender.
 * Currently it supports:
 *  - Get current weather
 */
public class OpenWeatherMap extends AbstractActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    private Http httpGate;
    private Materializer materializer;

    // Sender to which return the response
    private ActorRef target;

    // Actor's name
    public static String name = "openWeatherMap";

    public OpenWeatherMap(Http httpGate, Materializer materializer) {
        this.httpGate = httpGate;
        this.materializer = materializer;
    }

    static Props props(Http httpGate, Materializer materializer) {
        return Props.create(OpenWeatherMap.class, () -> new OpenWeatherMap(httpGate, materializer));
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(OpenWeatherMapCurrentWeatherRequest.class, request -> {
                    target = getSender();
                    requestCurrentWeather(request)
                            .thenAccept(resp -> {log.info(resp.toString()); target.tell(resp, getSelf());});
                })
                .build();
    }

    private CompletionStage<OpenWeatherMapCurrentWeatherResponse>
            requestCurrentWeather(OpenWeatherMapCurrentWeatherRequest request) {
        CompletionStage<HttpResponse> responseFuture = httpGate.singleRequest(request.create(), materializer);
        Unmarshaller<HttpEntity, OpenWeatherMapCurrentWeatherResponse> decoder
                = Jackson.unmarshaller(OpenWeatherMapCurrentWeatherResponse.class);
        return responseFuture.thenCompose(resp -> decoder.unmarshal(resp.entity(), this.materializer));
    }
}