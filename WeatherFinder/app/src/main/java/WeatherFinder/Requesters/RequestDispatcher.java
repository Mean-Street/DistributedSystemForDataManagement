package WeatherFinder.Requesters;

import WeatherFinder.Requests.ApixuRequestTemperature;
import WeatherFinder.Requests.OpenWeatherMapRequestTemperature;
import WeatherFinder.Requests.WeatherBitRequestTemperature;
import WeatherFinder.Responses.ApixuResponse;
import WeatherFinder.Responses.OpenWeatherMapResponse;
import WeatherFinder.Responses.WeatherBitResponse;
import WeatherFinder.Configurations.KafkaConfig;
import WeatherFinder.Responses.TwitterResponse;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.http.javadsl.Http;
import akka.stream.Materializer;
import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

public class RequestDispatcher extends AbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    private final ActorRef openWeatherMapActorRef;
    private final ActorRef weatherBitApiRef;
    private final ActorRef apixuApiRef;
    private final ActorRef twitterRef;

    public final static String name = "request-dispatcher";

    public static Props props(Http http, Materializer materializer, KafkaConfig config) {
        return Props.create(RequestDispatcher.class, () -> new RequestDispatcher(http, materializer, config));
    }

    private RequestDispatcher(Http http, Materializer materializer, KafkaConfig config) {
        this.openWeatherMapActorRef = getContext().actorOf(
                Requester.props(http, materializer, OpenWeatherMapRequestTemperature.class,
                                OpenWeatherMapResponse.class, config));

        this.weatherBitApiRef = getContext().actorOf(Requester.props(http, materializer, WeatherBitRequestTemperature.class,
                                                     WeatherBitResponse.class, config));

        this.apixuApiRef = getContext().actorOf(Requester.props(http, materializer, ApixuRequestTemperature.class,
                                                ApixuResponse.class, config));

        this.twitterRef = getContext().actorOf(RequesterTwitter.props(http, materializer, config));
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(OpenWeatherMapRequestTemperature.class, request -> {
                    log.info("Forwarding a request to OpenWeatherMap");
                    openWeatherMapActorRef.forward(request, getContext());
                })
                .match(WeatherBitRequestTemperature.class, request -> {
                    log.info("Forwarding a request to WeatherBit");
                    weatherBitApiRef.forward(request, getContext());
                })
                .match(ApixuRequestTemperature.class, request -> {
                    log.info("Forwarding a request to Apixu");
                    apixuApiRef.forward(request, getContext());
                })
                .matchEquals("start_twitter", s -> {
                    log.info("Forwarding a request to Apixu");
                    twitterRef.forward(s, getContext());
                })
                .build();
    }
}
