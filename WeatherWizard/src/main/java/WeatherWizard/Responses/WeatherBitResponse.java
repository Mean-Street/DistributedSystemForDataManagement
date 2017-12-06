package WeatherWizard.Responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import java.util.ArrayList;
import java.util.List;


/**
 * This class describes a response from WeatherBitRequester when queried with a Current Weather request.
 * Currently, it only stores:
 *  - Coordinates (longitude, latitude)
 *  - Temperature
 */

@JsonIgnoreProperties({"count"})
public class WeatherBitResponse extends Response {
    private List<Double> temperatures;
    private List<Coordinate> coordinates;

    public WeatherBitResponse() {
        temperatures = new ArrayList<>();
        coordinates = new ArrayList<>();
    }

    @Override
    public Double getTemperature() {
        return temperatures.get(0);
    }

    @JsonSetter("data")
    public void setData(final List<Entry> entries) {
        for (Entry e : entries) {
            temperatures.add(e.temp);
            coordinates.add(new Coordinate(e.lat, e.lon));
        }
    }

    @JsonIgnoreProperties({"rh","pod","pres","timezone","weather","code","description","country_code","clouds","vis","wind_spd","wind_cdir_full","app_temp","state_code","ts","elev_angle","h_angle","dewpt","ob_time","uv","sunset","sunrise","city_name","precip","station","dhi","datetime","wind_dir","slp","wind_cdir"})
    public static class Entry {
        public Double temp;
        public Double lat;
        public Double lon;
    }

    public static class Coordinate {
        public Double lon;
        public Double lat;

        Coordinate(double lat, double lon) {
            this.lat = lat;
            this.lon = lon;
        }

        @Override
        public String toString() {
            return "(" + lon.toString() + ", " + lat.toString() + ")";
        }
    }

    List<Double> getTemperatures() {
        return temperatures;
    }

    List<Coordinate> getCoordinates() {
        return coordinates;
    }
}
