package WeatherWizard.Configurations;

import org.aeonbits.owner.Config;

@Config.Sources({"file:./src/main/java/WeatherWizard/Configurations/OpenWeatherMapConfig.properties"})
public interface OpenWeatherMapConfig extends Config {
    String key();
}