package WeatherFinder.Requests;

import akka.http.javadsl.model.HttpRequest;

import static org.junit.jupiter.api.Assertions.*;

class ApixuRequestTemperatureTest {
    void whenCreatingMultipleRequests() {
        ApixuRequestTemperature request = new ApixuRequestTemperature(new Location("Roma", "it"));
        HttpRequest httpRequest = request.create();
        assertEquals(httpRequest, request.create());
    }

}