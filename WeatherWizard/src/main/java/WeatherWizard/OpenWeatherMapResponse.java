package WeatherWizard;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;


@JsonIgnoreProperties({"coord", "weather", "base", "visibility", "wind", "clouds", "dt", "sys", "id", "name", "cod"})
public class OpenWeatherMapResponse {

    private Double temperature;

    @JsonSetter("main")
    public void setTemperature(Main main) {
        this.temperature = main.temp;
    }

    @JsonIgnoreProperties({"pressure", "humidity", "temp_min", "temp_max"})
    public static class Main {
        public Double temp;
    }

    public Double getTemperature() {
        return temperature;
    }

    public String toString() {
        return temperature.toString();
    }
}
