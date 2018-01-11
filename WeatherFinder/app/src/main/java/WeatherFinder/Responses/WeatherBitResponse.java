package WeatherFinder.Responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 * This class describes a response from WeatherBitRequester when queried with a Current Weather request.
 * Currently, it only stores:
 *  - Coordinates (longitude, latitude)
 *  - Temperature
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherBitResponse extends Response {
    
    private final static String source = "WeatherBit";
    
    private List<Double> temperatures;
    private List<Coordinate> coordinates;
//    private LocalDateTime date;

    public WeatherBitResponse() {
        temperatures = new ArrayList<>();
        coordinates = new ArrayList<>();
    }

    @Override
    public Double getTemperature() {
        System.out.println("getTemperature()");
        return temperatures.get(0);
    }
    
    @Override
    public String getSource() {
        return source;
    }

    @JsonSetter("data")
    public void setData(final List<Entry> entries) {
        for (Entry e : entries) {
            temperatures.add(e.temp);
            coordinates.add(new Coordinate(e.lat, e.lon));
        }
    }

@JsonIgnoreProperties(ignoreUnknown = true)
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

    @Override
    public LocalDateTime getDate() {
        return LocalDateTime.now();
    }

    List<Coordinate> getCoordinates() {
        return coordinates;
    }
}
