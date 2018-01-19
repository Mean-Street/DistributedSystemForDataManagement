package WeatherFinder.Responses;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;

@JsonSerialize(using = ResponseSerializer.class)
public abstract class Response {
    public abstract String getSource();
    public abstract Double getTemperature();
    public abstract LocalDateTime getDate();
}
