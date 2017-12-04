package WeatherWizard.Responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
<<<<<<< HEAD
import java.util.ArrayList;
import java.util.List;
=======
>>>>>>> 63e4ec5c820b4064046bdfe72503b0a42fd3b552


/**
 * This class describes a response from WeatherBitRequester when queried with a Current Weather request.
 * Currently, it only stores:
 *  - Coordinates (longitude, latitude)
 *  - Temperature
 */
<<<<<<< HEAD

@JsonIgnoreProperties({"count"})
public class WeatherBitResponse extends Response {
    
//    public class Coordinates {
//        public Double lon;
//        public Double lat;
//
//        public Coordinates(double lat, double lon) {
//            this.lat = lat;
//            this.lon = lon;
//        }
//        
//        @Override
//        public String toString() {
//            return "(" + lon.toString() + ", " + lat.toString() + ")";
//        }
//    }
    
    public class Data {
        public List<Entry> values = new ArrayList<Entry>();
    }
    
    @JsonIgnoreProperties({"lat", "lon", "rh", "pod", "pres", "timezone", "weather", "country_code", "clouds", "vis", "wind_spd", "wind_cdir_full", "app_temp", "state_code", "ts", "elev_angle", "h_angle", "dewpt", "ob_time", "uv", "sunset", "sunrise", "city_name", "precip", "station", "dhi", "datetime", "wind_dir", "slp", "wind_cdir"})
    public class Entry {
        public Double temp;
//        public Double lat;
//        public Double lon;
=======
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
>>>>>>> 63e4ec5c820b4064046bdfe72503b0a42fd3b552
    }


    private Double temperature;
<<<<<<< HEAD
//    private Coordinates coord;

    @JsonSetter("data")
    public void setEntry(Data data) {
//        Coordinates coord = new Coordinates(data.values.get(0).lat, data.values.get(0).lon);
//        System.out.println("location set : " + coord.toString() + " " + data.values.get(0).temp);
//        this.coord = coord;
        this.temperature = data.values.get(0).temp;
=======
    private Coordinates location;

    public void setLocation(Coordinates location) {
        this.location = location;
        System.out.println("location set");
    }

    public void setTemperature(Current current) {
        this.temperature = current.temp;
        System.out.println("temperature set");
>>>>>>> 63e4ec5c820b4064046bdfe72503b0a42fd3b552
    }


    public Double getTemperature() {
        return temperature;
    }

<<<<<<< HEAD
//    public Coordinates getCoord() { return coord; }

    public String toString() {
        return getTemperature() + "°C, at: ";
=======
    public Coordinates getLocation() { return location; }

    public String toString() {
        return getTemperature() + "°C, at: " + getLocation();
>>>>>>> 63e4ec5c820b4064046bdfe72503b0a42fd3b552
    }
}
