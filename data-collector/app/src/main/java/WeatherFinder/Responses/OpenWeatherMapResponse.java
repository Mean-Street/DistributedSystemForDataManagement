package WeatherFinder.Responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * This class describes a response from OpenWeatherMapRequester when queried with a Current Weather request.
 * Currently, it only stores:
 *  - Coordinates (longitude, latitude)
 *  - Temperature
 */
@JsonIgnoreProperties({"weather", "base", "visibility", "wind", "clouds", "dt", "sys", "id", "name", "cod"})
public class OpenWeatherMapResponse extends Response {
    
    private final static String source = "OpenWeatherMap";
    
    public class Coordinates {
        public Double lon;
        public Double lat;

        @Override
        public String toString() {
            return "(" + lon.toString() + ", " + lat.toString() + ")";
        }
    }

    @JsonIgnoreProperties({"pressure", "humidity", "temp_min", "temp_max"})
    public class Main {
        public Double temp;
    }

    private Double temperature;
    private Coordinates coord;
//    private LocalDateTime date;

    @JsonSetter("coord")
    public void setCoord(Coordinates coord) {
        this.coord = coord;
    }

    @JsonSetter("main")
    public void setTemperature(Main main) {
        this.temperature = main.temp - 273.15;
    }

    @Override
    public Double getTemperature() {
        return temperature;
    }

    @Override
    public LocalDateTime getDate() {
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formatDateTime = date.format(formatter);
        date  = LocalDateTime.parse(formatDateTime, formatter);
        return date;
    }
    
    @Override
    public String getSource() {
        return source;
    }


    Coordinates getCoord() { return coord; }

    public String toString() {
        return getTemperature() + "K, at: " + getCoord();
    }
}
