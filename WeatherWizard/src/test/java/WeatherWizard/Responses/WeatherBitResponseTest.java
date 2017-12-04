package WeatherWizard.Responses;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class WeatherBitResponseTest {

    @Test
    public void whenDeserializingResponse() throws IOException {
        String jsonResponse = "{\"data\":[{\"rh\":87,\"pod\":\"d\",\"pres\":1021.64,\"timezone\":\"Europe\\/Paris\",\"weather\":{\"icon\":\"c03d\",\"code\":\"803\",\"description\":\"Broken clouds\"},\"country_code\":\"FR\",\"clouds\":75,\"vis\":10,\"wind_spd\":3.1,\"wind_cdir_full\":\"west-northwest\",\"app_temp\":9,\"lon\":2.3488,\"state_code\":\"11\",\"ts\":1512392400,\"elev_angle\":16,\"h_angle\":45,\"dewpt\":7,\"ob_time\":\"2017-12-04 13:00\",\"uv\":2,\"sunset\":\"15:55\",\"sunrise\":\"07:26\",\"city_name\":\"Paris\",\"precip\":null,\"station\":\"LFPO\",\"lat\":48.85341,\"dhi\":214,\"datetime\":\"2017-12-04:13\",\"temp\":9.1,\"wind_dir\":290,\"slp\":1033,\"wind_cdir\":\"WNW\"}],\"count\":1}";

        WeatherBitResponse response = new ObjectMapper()
                .readerFor(WeatherBitResponse.class)
                .readValue(jsonResponse);

        assertEquals(Double.valueOf(9.1), response.getTemperatures().get(0));
        assertEquals(Double.valueOf(2.3488), response.getCoordinates().get(0).lon);
        assertEquals(Double.valueOf(48.85341), response.getCoordinates().get(0).lat);
    }
}