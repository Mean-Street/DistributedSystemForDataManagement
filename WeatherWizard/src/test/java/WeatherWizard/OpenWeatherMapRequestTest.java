package WeatherWizard;

import akka.http.javadsl.model.HttpRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OpenWeatherMapRequestTest {

    @Test
    public void whenCreatingOpenWeatherMapTemperatureRequest() {
        String key = "123456789";
        OpenWeatherMapRequest request = new OpenWeatherMapRequest(key);
        HttpRequest httpRequest = request.create();

        assertEquals(httpRequest.getUri().scheme(), "http");
        assertTrue(httpRequest.getUri().rawQueryString().get().matches(".*appid=[0-9]+.*"));
    }
}