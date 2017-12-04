package WeatherWizard.Requests;

import akka.http.javadsl.model.HttpRequest;
import org.aeonbits.owner.ConfigFactory;
import WeatherWizard.Configurations.ApixuConfig;

public abstract class ApixuRequest {
    protected ApixuConfig config;
    private String urlRequest;

    protected ApixuRequest() {
        this.config = ConfigFactory.create(ApixuConfig.class);
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
        setUrl(getUrl() + "?" + config.keyTag() + "=" + config.key());
        configureUrl();
        System.out.println(getUrl());
        return HttpRequest.create(getUrl());
    }
}