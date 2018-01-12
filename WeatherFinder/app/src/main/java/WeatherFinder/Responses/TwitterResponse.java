package WeatherFinder.Responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    
    

    @JsonSetter("created_at")
    public void setDate(String useless) throws ParseException {
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formatDateTime = date.format(formatter);
        this.created_at = formatDateTime;
    }

}
