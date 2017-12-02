package WeatherWizard.OpenWeatherMap;

public class CurrentWeatherRequest extends Request {
    private String city;
    private String country;


    public CurrentWeatherRequest(String city, String country) {
        this.city = city;
        this.country = country;
    }

    @Override
    protected void configureUrl() {
//        setUrl(getUrl() + serverConfig.weatherTag());
//        setUrl(getUrl() + "?" + serverConfig.locationTag() + "=" + city + "," + country);
    }
}
