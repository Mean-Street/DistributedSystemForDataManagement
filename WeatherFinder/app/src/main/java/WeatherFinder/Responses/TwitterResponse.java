package WeatherFinder.Responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * This class describes a response from ApixuRequester when queried with a Current Weather request.
 * Currently, it only stores:
 *  - Coordinates (longitude, latitude)
 *  - Temperature
 */
public class TwitterResponse {
    
    public String created_at;
    public Double id;
    public String text;

}
