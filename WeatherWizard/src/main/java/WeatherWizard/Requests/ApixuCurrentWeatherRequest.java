package WeatherWizard.Requests;

public class ApixuCurrentWeatherRequest extends ApixuRequest {
    private String city;


    public ApixuCurrentWeatherRequest(String city) {
        this.city = city;
    }

    @Override
    protected void configureUrl() {
//        setUrl(getUrl() + config.weatherTag());
        setUrl(getUrl() + "&" + config.locationTag() + "=" + city);
    }
}