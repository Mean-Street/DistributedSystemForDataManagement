package WeatherWizard;

import akka.actor.AbstractActor;
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


public class OpenWeatherMap extends AbstractActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    private Http httpGate;
    private Materializer materializer;
    private OpenWeatherMapResponse response;

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
                .match(String.class, s -> {
                    log.info("Received String message: {}", s);
                })
                .match(OpenWeatherMapRequest.class, request -> {
                    requestOpenWeatherMapTemperature(request)
                            .thenAccept(x -> log.info("Received new temperature: " + x.getTemperature() + "K"));
                })
                .matchAny(o -> log.info("received unknown message"))
                .build();
    }

    private CompletionStage<OpenWeatherMapResponse> requestOpenWeatherMapTemperature(OpenWeatherMapRequest request) {
        CompletionStage<HttpResponse> responseFuture = httpGate.singleRequest(request.create(), materializer);
        log.info(responseFuture.toString());
        Unmarshaller<HttpEntity,OpenWeatherMapResponse> decoder = Jackson.unmarshaller(OpenWeatherMapResponse.class);
        return responseFuture.thenCompose(resp -> decoder.unmarshal(resp.entity(), this.materializer));
    }
}