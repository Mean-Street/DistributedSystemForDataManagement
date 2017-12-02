package WeatherWizard;

import akka.http.javadsl.model.HttpRequest;

public class OpenWeatherMapRequest {
    private String key;
    private String url = "http://api.openweathermap.org/data/2.5/weather?q=London,uk&appid=";


    public OpenWeatherMapRequest(String key) {
        this.key = key;
    }

    public HttpRequest create() {
        return HttpRequest.create(url + key);
    }
}
