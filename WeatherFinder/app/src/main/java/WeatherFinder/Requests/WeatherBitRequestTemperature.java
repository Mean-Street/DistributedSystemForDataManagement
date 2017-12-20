package WeatherFinder.Requests;

import akka.http.javadsl.model.HttpRequest;
import org.aeonbits.owner.ConfigFactory;
import WeatherFinder.Configurations.WeatherBitConfig;

public class WeatherBitRequestTemperature extends RequestTemperature {
    private WeatherBitConfig config;
    private String urlRequest;
    private Location location;


    public WeatherBitRequestTemperature(Location location) {
        this.config = ConfigFactory.create(WeatherBitConfig.class);
        this.urlRequest = config.url();
        this.location = location;
    }

    private String configureUrl() {
        return getUrl() + "?" + config.locationTag() + "=" + location.getCity();
    }

    private String getUrl() {
        return urlRequest;
    }

    @Override
    public Location getLocation() {
        return null;
    }

    @Override
    public HttpRequest create() {
        return HttpRequest.create(configureUrl() + "&" + config.keyTag() + "=" + config.key());
    }
}