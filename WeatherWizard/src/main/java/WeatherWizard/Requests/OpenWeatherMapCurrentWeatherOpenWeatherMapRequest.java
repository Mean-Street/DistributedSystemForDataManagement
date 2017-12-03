package WeatherWizard.Requests;

public class OpenWeatherMapCurrentWeatherOpenWeatherMapRequest extends OpenWeatherMapRequest {
    private String city;
    private String country;


    public OpenWeatherMapCurrentWeatherOpenWeatherMapRequest(String city, String country) {
        this.city = city;
        this.country = country;
    }

    @Override
    protected void configureUrl() {
        setUrl(getUrl() + config.weatherTag());
        setUrl(getUrl() + "?" + config.locationTag() + "=" + city + "," + country);
    }
}