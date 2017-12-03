package WeatherWizard.Requests;

public class OpenWeatherMapCurrentWeatherRequest extends OpenWeatherMapRequest {
    private String city;
    private String country;


    public OpenWeatherMapCurrentWeatherRequest(String city, String country) {
        this.city = city;
        this.country = country;
    }

    @Override
    protected void configureUrl() {
        setUrl(getUrl() + config.weatherTag());
        setUrl(getUrl() + "?" + config.locationTag() + "=" + city + "," + country);
    }
}