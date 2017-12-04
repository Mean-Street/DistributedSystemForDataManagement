package WeatherWizard.Requests;

import akka.http.javadsl.model.HttpRequest;
import org.aeonbits.owner.ConfigFactory;
import WeatherWizard.Configurations.WeatherBitConfig;

public abstract class WeatherBitRequest {
    protected WeatherBitConfig config;
    private String urlRequest;

    protected WeatherBitRequest() {
        this.config = ConfigFactory.create(WeatherBitConfig.class);
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
        System.out.println("Url : " + getUrl() + "&" + config.keyTag() + "=" + config.key());
        return HttpRequest.create(getUrl() + "&" + config.keyTag() + "=" + config.key());
    }
}