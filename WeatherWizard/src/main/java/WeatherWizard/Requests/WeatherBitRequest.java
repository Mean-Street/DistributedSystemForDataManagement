package WeatherWizard.Requests;

import akka.http.javadsl.model.HttpRequest;
import org.aeonbits.owner.ConfigFactory;
import WeatherWizard.Configurations.WeatherBitConfig;

public class WeatherBitRequest extends Request {
    private WeatherBitConfig config;
    private String urlRequest;
    private String city;


    public WeatherBitRequest(String city) {
        this.config = ConfigFactory.create(WeatherBitConfig.class);
        this.urlRequest = config.url();
        this.city = city;
    }

    private void configureUrl() {
        setUrl(getUrl() + "?" + config.locationTag() + "=" + city);
    }

    private String getUrl() {
        return urlRequest;
    }

    private void setUrl(String url) {
        this.urlRequest = url;
    }

    @Override
    public HttpRequest create() {
        configureUrl();
        System.out.println("Url : " + getUrl() + "&" + config.keyTag() + "=" + config.key());
        return HttpRequest.create(getUrl() + "&" + config.keyTag() + "=" + config.key());
    }
}