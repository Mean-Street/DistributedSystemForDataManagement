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

    private void configureUrl() {
        setUrl(getUrl() + "?" + config.locationTag() + "=" + location.getCity());
    }

    @Override
    public Location getLocation() {
        return location;
    }

    private String getUrl() {
        return urlRequest;
    }

    private void setUrl(String url) {
        this.urlRequest = url;
    }

    public HttpRequest create() {
        setUrl(getUrl() + "?" + config.keyTag() + "=" + config.key());
        configureUrl();
        System.out.println(getUrl());
        return HttpRequest.create(getUrl());
    }
}