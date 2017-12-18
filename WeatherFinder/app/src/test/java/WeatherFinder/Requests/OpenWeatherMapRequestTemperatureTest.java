package WeatherFinder.Requests;

import akka.http.javadsl.model.HttpRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OpenWeatherMapRequestTemperatureTest {

    @Test
    public void whenCreatingCurrentWeatherRequest() {
        OpenWeatherMapRequestTemperature request
                = new OpenWeatherMapRequestTemperature(new Location("Roma", "it"));
        HttpRequest httpRequest = request.create();

        assertEquals(httpRequest.getUri().scheme(), "http");
        assertTrue(httpRequest.getUri().rawQueryString().get().matches(".*appid=[0-9a-z]+.*"));
    }

    @Test
    public void whenCreatingMultipleRequests() {
        OpenWeatherMapRequestTemperature request
                = new OpenWeatherMapRequestTemperature(new Location("Roma", "it"));
        HttpRequest httpRequest = request.create();
        assertEquals(httpRequest, request.create());
    }
}