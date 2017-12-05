package WeatherWizard.Requests;

import akka.http.javadsl.model.HttpRequest;
import org.aeonbits.owner.ConfigFactory;
import WeatherWizard.Configurations.WeatherBitConfig;

public class WeatherBitRequest extends Request {
    private WeatherBitConfig config;
    private String urlRequest;
    private Location location;


    public WeatherBitRequest(Location location) {
        this.config = ConfigFactory.create(WeatherBitConfig.class);
        this.urlRequest = config.url();
        this.location = location;
    }

    private void configureUrl() {
        setUrl(getUrl() + "?" + config.locationTag() + "=" + location.getCity());
    }

    private String getUrl() {
        return urlRequest;
    }

    @Override
    public Location getLocation() {
        return null;
    }

    private void setUrl(String url) {
        this.urlRequest = url;
    }

    @Override
    public HttpRequest create() {
        configureUrl();
        return HttpRequest.create(getUrl() + "&" + config.keyTag() + "=" + config.key());
    }
}