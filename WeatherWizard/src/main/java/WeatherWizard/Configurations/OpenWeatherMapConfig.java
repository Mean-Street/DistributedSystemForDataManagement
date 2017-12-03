package WeatherWizard.Configurations;

import org.aeonbits.owner.Config;

@Config.Sources({"file:./src/main/java/WeatherWizard/Configurations/OpenWeatherMapConfig.properties"})
public interface OpenWeatherMapConfig extends Config {
    @Key("key_tag")
    String keyTag();

    String key();

    String url();

    @Key("location_tag")
    String locationTag();

    @Key("weather_tag")
    String weatherTag();

}