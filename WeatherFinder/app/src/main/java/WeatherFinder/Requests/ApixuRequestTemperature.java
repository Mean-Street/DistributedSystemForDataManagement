package WeatherFinder.Requests;

import akka.http.javadsl.model.HttpRequest;
import org.aeonbits.owner.ConfigFactory;
import WeatherFinder.Configurations.ApixuConfig;

public class ApixuRequestTemperature extends RequestTemperature {
    private ApixuConfig config;
    private Location location;
    private final String url = "http://api.apixu.com/v1/current.json";

    
    public ApixuRequestTemperature(Location location) {
        this.config = ConfigFactory.create(ApixuConfig.class);
        this.location = location;
    }

    private String urlAddLocation(String url) {
        return url + "&q=" + location.getCity();
    }

    private String urlAddKey(String url) {
        return url + "?key=" + config.key();
    }

    @Override
    public Location getLocation() {
        return location;
    }

    private String getUrl() {
        return url;
    }

    public HttpRequest create() {
        String url = urlAddKey(getUrl());
        url = urlAddLocation(url);
        return HttpRequest.create(url);
    }
}