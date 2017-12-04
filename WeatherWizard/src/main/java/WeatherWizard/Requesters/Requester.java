package WeatherWizard.Requesters;


import WeatherWizard.Requests.Location;
import WeatherWizard.Requests.OpenWeatherMapRequest;
import WeatherWizard.Requests.Request;
import WeatherWizard.Responses.Response;
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

public class Requester<RequestClass extends Request, ResponseClass extends Response> extends AbstractActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    private Http httpGate;
    private Materializer materializer;
    private Class<RequestClass> requestClass;
    private Class<ResponseClass> responseClass;

    // Sender to which return the response
    private ActorRef target;

    private Requester(Http httpGate, Materializer materializer, Class<RequestClass> reqClass, Class<ResponseClass> respClass) {
        this.requestClass = reqClass;
        this.responseClass = respClass;
        this.httpGate = httpGate;
        this.materializer = materializer;
    }

    // ATTENTION? passé en public pour le TIMER
    public static <ReqType extends Request, RespType extends Response> Props props(
            Http httpGate, Materializer materializer, Class<ReqType> reqClass, Class<RespType> respClass) {
        return Props.create(Requester.class, () -> new Requester<>(httpGate, materializer, reqClass, respClass));
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(requestClass, request -> {
                    target = getSender();
                    requestCurrentWeather(request)
                            .thenAccept(resp -> {log.info(resp.toString()); target.tell(resp, getSelf());});
                })
                .matchEquals("Tick", request -> {
                    target = getSender();
                    OpenWeatherMapRequest req = new OpenWeatherMapRequest(new Location("Paris", "fr"));
                    requestCurrentWeather((RequestClass) req)
                            .thenAccept(resp -> {log.info(resp.toString()); target.tell(resp, getSelf());});
                })
                .build();
    }

    private CompletionStage requestCurrentWeather(RequestClass request) {
        CompletionStage<HttpResponse> responseFuture = httpGate.singleRequest(request.create(), materializer);
        Unmarshaller<HttpEntity, ResponseClass> decoder = Jackson.unmarshaller(responseClass);
        return responseFuture.thenCompose(resp -> decoder.unmarshal(resp.entity(), this.materializer));
    }
}