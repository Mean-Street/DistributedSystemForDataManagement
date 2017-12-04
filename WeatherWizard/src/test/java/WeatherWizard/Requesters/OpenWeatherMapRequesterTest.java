package WeatherWizard.Requesters;

import WeatherWizard.Requests.Location;
import WeatherWizard.Requests.OpenWeatherMapCurrentWeatherRequest;
import WeatherWizard.Responses.OpenWeatherMapCurrentWeatherResponse;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.http.javadsl.Http;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;
import akka.testkit.javadsl.TestKit;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OpenWeatherMapRequesterTest {

    static ActorSystem system;

    @BeforeAll
    public static void setup() {
        system = ActorSystem.create("test_openWeatherMap");
    }

    @AfterAll
    public static void teardown() {
        TestKit.shutdownActorSystem(system);
        system = null;
    }

    /**
     * Tests a valid Current Weather request/response scenario.
     */
    @Test
    public void whenSendingValidCurrentWeatherRequest() {
        new TestKit(system) {{
            final Http httpGate = Http.get(system);
            final Materializer materializer = ActorMaterializer.create(system);
            final ActorRef openWeatherActorRef = system.actorOf(OpenWeatherMapRequester.props(httpGate, materializer));

            OpenWeatherMapCurrentWeatherRequest request = new OpenWeatherMapCurrentWeatherRequest(new Location("Roma", "it"));

            openWeatherActorRef.tell(request, getRef());
            expectMsgClass(OpenWeatherMapCurrentWeatherResponse.class);
        }};
    }

    /**
     * Tests an invalid Current Weather request/response scenario.
     */
    @Test
    public void whenSendingInvalidCurrentWeatherRequest() {
        new TestKit(system) {{
            final Http httpGate = Http.get(system);
            final Materializer materializer = ActorMaterializer.create(system);
            final ActorRef openWeatherActorRef = system.actorOf(OpenWeatherMapRequester.props(httpGate, materializer));

            OpenWeatherMapCurrentWeatherRequest request = new OpenWeatherMapCurrentWeatherRequest(new Location("Roma", "fr"));

            openWeatherActorRef.tell(request, getRef());
            expectNoMsg();
        }};
    }
}