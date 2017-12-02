package WeatherWizard.OpenWeatherMap;

import org.aeonbits.owner.Config;

/**
 location_tag=q
 weather_tag=weather
 key_tag=appid
 key=7f484592e9396fae071a8f19acdc0e71
 url=http://api.openweathermap.org/data/2.5/
 */
public interface ServerConfig extends Config {
    int port();
    String hostname();
    int maxThreads();
//     @Key("key_tag")
//     String keyTag();
//
//     String key();
//
     // String url();
//
//     @Key("location_tag")
    //String locationTag();

    //@Key("weather_tag")
    //String weatherTag();

}
