package WeatherWizard.Requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Location {
    private final String city;
    private final String country;

    @JsonCreator
    public Location(@JsonProperty("city") String city,
             @JsonProperty("country") String country) {
        this.city = city;
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public String toString() {
        return city + ", " + country;
    }
}
