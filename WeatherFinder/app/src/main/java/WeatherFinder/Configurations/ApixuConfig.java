package WeatherFinder.Configurations;

import org.aeonbits.owner.Config;

@Config.Sources({"file:./src/main/java/WeatherFinder/Configurations/ApixuConfig.properties"})
public interface ApixuConfig extends Config {
    String key();
}
