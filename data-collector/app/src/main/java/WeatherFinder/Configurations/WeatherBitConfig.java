package WeatherFinder.Configurations;

import org.aeonbits.owner.Config;

@Config.Sources({"file:./src/main/java/WeatherFinder/Configurations/WeatherBitConfig.properties"})
public interface WeatherBitConfig extends Config {
    @Config.Key("key_tag")
    String keyTag();

    String key();

    String url();

    @Config.Key("location_tag")
    String locationTag();

//    @Config.Key("weather_tag")
//    String weatherTag();

}
