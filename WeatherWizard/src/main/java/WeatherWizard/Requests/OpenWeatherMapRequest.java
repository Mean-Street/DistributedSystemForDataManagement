package WeatherWizard.Requests;

import WeatherWizard.Configurations.OpenWeatherMapConfig;
import akka.http.javadsl.model.HttpRequest;
import org.aeonbits.owner.ConfigFactory;

public class OpenWeatherMapRequest extends Request {
    private Location location;
    private OpenWeatherMapConfig config;

    public OpenWeatherMapRequest(Location location) {
        this.config = ConfigFactory.create(OpenWeatherMapConfig.class);
        this.location = location;
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public HttpRequest create() {
        String key_tag = "appid";
        String url = "http://api.openweathermap.org/data/2.5/";

        String configUrl = configureUrl(url);
        return HttpRequest.create(configUrl + "&" + key_tag + "=" + config.key());
    }

    private String configureUrl(String baseUrl) {
        String location_tag = "q";
        String weather_tag = "weather";

        baseUrl += weather_tag;
        return baseUrl + "?" + location_tag + "=" + location.getCity() + "," + location.getCountry();
    }
}