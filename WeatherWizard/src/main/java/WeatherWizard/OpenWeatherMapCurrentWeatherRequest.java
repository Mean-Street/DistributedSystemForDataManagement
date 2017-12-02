package WeatherWizard;

public class OpenWeatherMapCurrentWeatherRequest extends OpenWeatherMapRequest {
    private String city;
    private String country;


    public OpenWeatherMapCurrentWeatherRequest(String key, String city, String country) {
        super(key);
        this.city = city;
        this.country = country;
    }

    @Override
    protected void configureUrl() {
        setUrl(getUrl() + "weather");
        setUrl(getUrl() + "?q=" + city + "," + country);
    }
}
