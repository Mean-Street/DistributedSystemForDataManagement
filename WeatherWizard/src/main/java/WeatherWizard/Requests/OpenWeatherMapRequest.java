package WeatherWizard.Requests;

import WeatherWizard.Configurations.OpenWeatherMapConfig;
import akka.http.javadsl.model.HttpRequest;
import org.aeonbits.owner.ConfigFactory;

public abstract class OpenWeatherMapRequest {

    protected OpenWeatherMapConfig config;

    protected OpenWeatherMapRequest() {
        this.config = ConfigFactory.create(OpenWeatherMapConfig.class);
    }

    protected abstract String configureUrl(String baseUrl);

    public HttpRequest create() {
        String key_tag = "appid";
        String url = "http://api.openweathermap.org/data/2.5/";

        String configUrl = configureUrl(url);
        return HttpRequest.create(configUrl + "&" + key_tag + "=" + config.key());
    }
}