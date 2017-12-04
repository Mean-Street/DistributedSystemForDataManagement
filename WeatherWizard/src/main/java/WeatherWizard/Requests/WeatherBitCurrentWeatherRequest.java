package WeatherWizard.Requests;

public class WeatherBitCurrentWeatherRequest extends WeatherBitRequest {
    private String city;


    public WeatherBitCurrentWeatherRequest(String city) {
        this.city = city;
    }

    @Override
    protected void configureUrl() {
//        setUrl(getUrl() + config.weatherTag());
        setUrl(getUrl() + "?" + config.locationTag() + "=" + city);
    }
}