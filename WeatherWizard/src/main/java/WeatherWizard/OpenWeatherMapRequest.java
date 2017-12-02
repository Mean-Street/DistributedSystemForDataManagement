package WeatherWizard;

import akka.http.javadsl.model.HttpRequest;

public abstract class OpenWeatherMapRequest {
    private String url = "http://api.openweathermap.org/data/2.5/";
    private String key;

    protected OpenWeatherMapRequest(String key) {
        this.key = key;
    }

    protected String getUrl() {
        return url;
    }

    protected void setUrl(String url) {
        this.url = url;
    }

    protected abstract void configureUrl();

    public HttpRequest create() {
        configureUrl();
        return HttpRequest.create(getUrl() + "&appid=" + key);
    }
}
