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
    public void setCoord(String date) throws ParseException {
        date = date.replace("+0000", "");
        date = date.substring(4);
        SimpleDateFormat ft = new SimpleDateFormat("MMM dd hh:mm:ss yyyy");
        java.util.Date t=ft.parse(date);
        ft.applyPattern("yyyy-MM-dd HH:mm");
        date = ft.format(t);
        date = date.replace(" ", "T");
        this.created_at = date;
    }

}
