package WeatherWizard.OpenWeatherMap;

import akka.http.javadsl.model.HttpRequest;
import org.aeonbits.owner.ConfigFactory;

public abstract class Request {
    protected ServerConfig serverConfig;
    private String urlRequest;

    protected Request() {
        ServerConfig cfg = ConfigFactory.create(ServerConfig.class);
        System.out.println("Server " + cfg.hostname() + ":" + cfg.port() +
                " will run " + cfg.maxThreads());

        // this.serverConfig = ConfigFactory.create(ServerConfig.class);
        // System.out.println("config: " + this.serverConfig);
        // this.urlRequest = serverConfig.url();
    }

    protected String getUrl() {
        return urlRequest;
    }

    protected void setUrl(String url) {
        this.urlRequest = url;
    }

    protected abstract void configureUrl();

    public String toString() {
        // return getUrl() + "&" + serverConfig.keyTag() + "=" + serverConfig.key();
        return "";
    }

    public HttpRequest create() {
        configureUrl();
        // return HttpRequest.create(getUrl() + "&" + serverConfig.keyTag() + "=" + serverConfig.key());
        return null;
    }
}
