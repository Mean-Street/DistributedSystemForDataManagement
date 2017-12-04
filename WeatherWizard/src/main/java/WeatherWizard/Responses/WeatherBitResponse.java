package WeatherWizard.Responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;


/**
 * This class describes a response from WeatherBitRequester when queried with a Current Weather request.
 * Currently, it only stores:
 *  - Coordinates (longitude, latitude)
 *  - Temperature
 */
@JsonIgnoreProperties({"count", "rh", "pod", "press", "timezone", "weather", "country_code", "clouds", "vis", "wind_spd", "wind_cdir_full", "app_temp", "state_code", "ts", "elev_angle", "h_angle", "dewpt", "ob_time", "uv", "sunset", "sunrise", "city_name", "precip", "station", "dhi", "datetime", "wind_dir", "slp", "wind_cdir"})
public class WeatherBitResponse extends Response {
    
    public class Coordinates {
        public Double lon;
        public Double lat;

        @Override
        public String toString() {
            return "(" + lon.toString() + ", " + lat.toString() + ")";
        }
    }

    public class Current {
        public Double temp;
    }


    private Double temperature;
    private Coordinates location;

    public void setLocation(Coordinates location) {
        this.location = location;
        System.out.println("location set");
    }

    public void setTemperature(Current current) {
        this.temperature = current.temp;
        System.out.println("temperature set");
    }


    public Double getTemperature() {
        return temperature;
    }

    public Coordinates getLocation() { return location; }

    public String toString() {
        return getTemperature() + "°C, at: " + getLocation();
    }
}
