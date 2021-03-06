package WeatherFinder.Responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * This class describes a response from ApixuRequester when queried with a Current Weather request.
 * Currently, it only stores:
 *  - Coordinates (longitude, latitude)
 *  - Temperature
 */
public class ApixuResponse extends Response {
    
    private final static String source = "Apixu";
    
    @JsonIgnoreProperties({"name", "region", "country", "tz_id", "localtime_epoch", "localtime"})
    public class Coordinates {
        public Double lon;
        public Double lat;

        @Override
        public String toString() {
            return "(" + lon.toString() + ", " + lat.toString() + ")";
        }
    }

    @JsonIgnoreProperties({"last_updated_epoch", "temp_f", "is_day", "condition", "wind_mph", "wind_kph", "wind_degree", "wind_dir", "pressure_mb", "pressure_in", "precip_mm", "precip_in", "humidity", "cloud", "feelslike_c", "feelslike_f", "vis_km", "vis_miles"})
    public class Current {
        public Double temp_c;
        public String last_updated;
    }


    private Double temperature;
    private Coordinates location;

    @JsonSetter("location")
    public void setLocation(Coordinates location) {
        this.location = location;
    }

    @JsonSetter("current")
    public void setTemperature(Current current) {
        this.temperature = current.temp_c;
    }

    @Override
    public Double getTemperature() {
        return temperature;
    }
    
    @Override
    public String getSource() {
        return source;
    }

    public Coordinates getLocation() { return location; }
    
    @Override
    public LocalDateTime getDate() {
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formatDateTime = date.format(formatter);
        date  = LocalDateTime.parse(formatDateTime, formatter);
        return date;
    }

    public String toString() {
        return getTemperature() + "°C, at: " + getLocation();
    }
}
