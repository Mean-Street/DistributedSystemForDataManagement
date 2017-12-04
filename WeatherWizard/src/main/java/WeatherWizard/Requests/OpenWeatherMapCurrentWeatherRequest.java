package WeatherWizard.Requests;

public class OpenWeatherMapCurrentWeatherRequest extends OpenWeatherMapRequest {
    private Location location;

    public OpenWeatherMapCurrentWeatherRequest(Location location) {
        this.location = location;
    }

    @Override
    protected String configureUrl(String baseUrl) {
        String location_tag = "q";
        String weather_tag = "weather";

        baseUrl += weather_tag;
        return baseUrl + "?" + location_tag + "=" + location.getCity() + "," + location.getCountry();
    }
}