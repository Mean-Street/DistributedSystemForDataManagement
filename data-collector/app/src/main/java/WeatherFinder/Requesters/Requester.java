package WeatherFinder.Requesters;


import WeatherFinder.Configurations.KafkaConfig;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.concurrent.CompletionStage;

public class Requester<RequestClass extends RequestTemperature, ResponseClass extends Response> extends AbstractActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    private final Http httpGate;
    private final Materializer materializer;
    private final Class<RequestClass> requestClass;
    private final Class<ResponseClass> responseClass;
    private final Producer<String, String> producer;
    private final KafkaConfig config;

    private static KafkaProducer<String, String> initProducer(KafkaConfig config) {

        Properties props = new Properties();
        props.put("bootstrap.servers", config.getBroker());
        props.put("client.id", "Producer");
        props.put("max.block.ms", "5000");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return new KafkaProducer<>(props);
    }

    private Requester(Http httpGate, Materializer materializer, Class<RequestClass> reqClass,
                      Class<ResponseClass> respClass, KafkaConfig config) {
        this.requestClass = reqClass;
        this.responseClass = respClass;
        this.httpGate = httpGate;
        this.materializer = materializer;
        this.config = config;
        this.producer = initProducer(config);
    }

    static <ReqType extends RequestTemperature, RespType extends Response> Props props(
            Http httpGate, Materializer materializer, Class<ReqType> reqClass, Class<RespType> respClass,
            KafkaConfig config) {
        return Props.create(Requester.class, () -> new Requester<>(httpGate, materializer, reqClass, respClass,
                                                                   config));
    }

    @Override
    public Receive createReceive() {
//        log.info("createReceive");
        return receiveBuilder()

                .match(requestClass, request -> {
                    requestCurrentWeather(request).thenAccept(resp -> {
                        try {
                            String responseSerialized = new ObjectMapper().writeValueAsString(resp);
                            ProducerRecord<String, String> newRecord = new ProducerRecord<>(
                                    config.getTopic(), request.getLocation().getCity(), responseSerialized);
//                            log.info("Got a response from: " + request.getLocation().getCity());
//                            log.info("Writing response on: " + config.getTopic());
                            log.info("response: " + newRecord);
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
