package WeatherWizard;

import akka.http.javadsl.model.HttpRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OpenWeatherMapCurrentWeatherRequestTest {

    @Test
    public void whenCreatingCurrentWeatherRequest() {
        String key = "123456789abc";
        OpenWeatherMapCurrentWeatherRequest request
                = new OpenWeatherMapCurrentWeatherRequest(key, "Roma", "it");
        HttpRequest httpRequest = request.create();

        assertEquals(httpRequest.getUri().scheme(), "http");
        assertTrue(httpRequest.getUri().rawQueryString().get().matches(".*appid=[0-9a-z]+.*"));
    }
}