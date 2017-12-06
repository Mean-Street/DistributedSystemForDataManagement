package WeatherFinder.Configurations;

import org.aeonbits.owner.Config;

@Config.Sources({"file:./src/main/java/WeatherFinder/Configurations/OpenWeatherMapConfig.properties"})
public interface OpenWeatherMapConfig extends Config {
    String key();
}
