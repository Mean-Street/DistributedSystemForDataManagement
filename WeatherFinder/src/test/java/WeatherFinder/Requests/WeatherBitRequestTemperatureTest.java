package WeatherFinder.Requests;

import akka.http.javadsl.model.HttpRequest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WeatherBitRequestTemperatureTest {
    @Test
    void whenCreatingMultipleRequests() {
        WeatherBitRequestTemperature request = new WeatherBitRequestTemperature(new Location("Roma", "it"));
        HttpRequest httpRequest = request.create();
        assertEquals(httpRequest, request.create());
    }
}
