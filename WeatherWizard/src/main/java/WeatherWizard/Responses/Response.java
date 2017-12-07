package WeatherWizard.Responses;

import java.time.LocalDateTime;

public abstract class Response {
    public abstract Double getTemperature();
    public abstract LocalDateTime getDate();
}
