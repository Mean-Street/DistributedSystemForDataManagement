package WeatherWizard.Requests;

import akka.http.javadsl.model.HttpRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OpenWeatherMapCurrentWeatherOpenWeatherMapRequestTest {

    @Test
    public void whenCreatingCurrentWeatherRequest() {
        OpenWeatherMapCurrentWeatherOpenWeatherMapRequest request
                = new OpenWeatherMapCurrentWeatherOpenWeatherMapRequest( "Roma", "it");
        HttpRequest httpRequest = request.create();

        assertEquals(httpRequest.getUri().scheme(), "http");
        assertTrue(httpRequest.getUri().rawQueryString().get().matches(".*appid=[0-9a-z]+.*"));
    }
}