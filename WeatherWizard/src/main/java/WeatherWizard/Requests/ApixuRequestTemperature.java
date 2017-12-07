package WeatherWizard.Requests;

import akka.http.javadsl.model.HttpRequest;
import org.aeonbits.owner.ConfigFactory;
import WeatherWizard.Configurations.ApixuConfig;

public class ApixuRequestTemperature extends RequestTemperature {
    private ApixuConfig config;
    private String urlRequest;
    private Location location;

    
    public ApixuRequestTemperature(Location location) {
        this.config = ConfigFactory.create(ApixuConfig.class);
        this.urlRequest = config.url();
        this.location = location;
    }

    private String urlAddLocation(String url) {
        return url + "&" + config.locationTag() + "=" + location.getCity();
    }

    private String urlAddKey(String url) {
        return url + "?" + config.keyTag() + "=" + config.key();
    }

    @Override
    public Location getLocation() {
        return location;
    }

    private String getUrl() {
        return urlRequest;
    }

    public HttpRequest create() {
        String url = urlAddKey(getUrl());
        url = urlAddLocation(url);
        return HttpRequest.create(url);
    }
}