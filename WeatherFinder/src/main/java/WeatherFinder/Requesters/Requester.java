package WeatherFinder.Requesters;


import WeatherFinder.Requests.RequestTemperature;
import WeatherFinder.Responses.Response;
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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.concurrent.CompletionStage;

public class Requester<RequestClass extends RequestTemperature, ResponseClass extends Response> extends AbstractActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    private final Http httpGate;
    private final Materializer materializer;
    private final Class<RequestClass> requestClass;
    private final Class<ResponseClass> responseClass;
    private final Producer<String, String> producer;

    private Requester(Http httpGate, Materializer materializer, Class<RequestClass> reqClass,
                      Class<ResponseClass> respClass, KafkaProducer<String, String> producer) {
        this.requestClass = reqClass;
        this.responseClass = respClass;
        this.httpGate = httpGate;
        this.materializer = materializer;
        this.producer = producer;
    }

    static <ReqType extends RequestTemperature, RespType extends Response> Props props(
            Http httpGate, Materializer materializer, Class<ReqType> reqClass, Class<RespType> respClass,
            KafkaProducer<String, String> producer) {
        return Props.create(Requester.class, () -> new Requester<>(httpGate, materializer, reqClass, respClass,
                                                                   producer));
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()

                .match(requestClass, request -> {
                    requestCurrentWeather(request).thenAccept(resp -> {
                        try {
                            String responseSerialized = new ObjectMapper().writeValueAsString(resp);
                            ProducerRecord<String, String> newRecord = new ProducerRecord<>(
                                    request.getTopic().toString(), request.getLocation().getCity(), responseSerialized);
                            log.info("Got a response from: " + request.getLocation().getCity());
                            producer.send(newRecord);
                        } catch (JsonProcessingException e) {
                            log.error("Response encountered an error during serialization");
                        }
                    });
                })
                .build();
    }

    private CompletionStage<ResponseClass> requestCurrentWeather(RequestClass request) {
        CompletionStage<HttpResponse> responseFuture = httpGate.singleRequest(request.create(), materializer);
        Unmarshaller<HttpEntity, ResponseClass> decoder = Jackson.unmarshaller(responseClass);
        return responseFuture.thenCompose(resp -> decoder.unmarshal(resp.entity(), this.materializer));
    }
}