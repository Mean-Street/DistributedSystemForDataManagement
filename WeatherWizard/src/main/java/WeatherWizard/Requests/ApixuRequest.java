package WeatherWizard.Requests;

import akka.http.javadsl.model.HttpRequest;
import org.aeonbits.owner.ConfigFactory;
import WeatherWizard.Configurations.ApixuConfig;

public class ApixuRequest extends Request{
    private ApixuConfig config;
    private String urlRequest;
    private String city;

    
    public ApixuRequest(String city) {
        this.config = ConfigFactory.create(ApixuConfig.class);
        this.urlRequest = config.url();
        this.city = city;
    }

    private void configureUrl() {
        setUrl(getUrl() + "?" + config.locationTag() + "=" + city);
    }

    protected String getUrl() {
        return urlRequest;
    }

    protected void setUrl(String url) {
        this.urlRequest = url;
    }

    public HttpRequest create() {
        setUrl(getUrl() + "?" + config.keyTag() + "=" + config.key());
        configureUrl();
        System.out.println(getUrl());
        return HttpRequest.create(getUrl());
    }
}