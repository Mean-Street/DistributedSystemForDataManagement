package WeatherWizard.Requests;

import WeatherWizard.Configurations.OpenWeatherMapConfig;
import akka.http.javadsl.model.HttpRequest;
import org.aeonbits.owner.ConfigFactory;

public abstract class OpenWeatherMapRequest {
    protected OpenWeatherMapConfig config;
    private String urlRequest;

    protected OpenWeatherMapRequest() {
        this.config = ConfigFactory.create(OpenWeatherMapConfig.class);
        this.urlRequest = config.url();
    }

    protected String getUrl() {
        return urlRequest;
    }

    protected void setUrl(String url) {
        this.urlRequest = url;
    }

    protected abstract void configureUrl();

    public HttpRequest create() {
        configureUrl();
        return HttpRequest.create(getUrl() + "&" + config.keyTag() + "=" + config.key());
    }
}