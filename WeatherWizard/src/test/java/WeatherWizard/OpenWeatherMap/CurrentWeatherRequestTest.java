package WeatherWizard.OpenWeatherMap;

import akka.http.javadsl.model.HttpRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CurrentWeatherRequestTest {

    @Test
    public void whenCreatingCurrentWeatherRequest() {
        String key = "123456789abc";
        CurrentWeatherRequest request
                = new CurrentWeatherRequest( "Roma", "it");
        HttpRequest httpRequest = request.create();

        assertEquals(httpRequest.getUri().scheme(), "http");
        assertTrue(httpRequest.getUri().rawQueryString().get().matches(".*appid=[0-9a-z]+.*"));
    }
}