package WeatherWizard.Responses;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class OpenWeatherMapResponseTest {

    @Test
    public void whenDeserializingCurrentWeatherResponse() throws IOException {
        String jsonResponse = "{\"coord\":{\"lon\":-0.13,\"lat\":51.51},\"weather\":[{\"id\":300,\"main\":\"Drizzle\",\"description\":\"light intensity drizzle\",\"icon\":\"09d\"}],\"base\":\"stations\",\"main\":{\"temp\":280.32,\"pressure\":1012,\"humidity\":81,\"temp_min\":279.15,\"temp_max\":281.15},\"visibility\":10000,\"wind\":{\"speed\":4.1,\"deg\":80},\"clouds\":{\"all\":90},\"dt\":1485789600,\"sys\":{\"type\":1,\"id\":5091,\"message\":0.0103,\"country\":\"GB\",\"sunrise\":1485762037,\"sunset\":1485794875},\"id\":2643743,\"name\":\"London\",\"cod\":200}";

        OpenWeatherMapResponse response = new ObjectMapper()
                .readerFor(OpenWeatherMapResponse.class)
                .readValue(jsonResponse);

        assertEquals(Double.valueOf(280.32), response.getTemperature());
        assertEquals(Double.valueOf(-0.13), response.getCoord().lon);
        assertEquals(Double.valueOf(51.51), response.getCoord().lat);
    }
}