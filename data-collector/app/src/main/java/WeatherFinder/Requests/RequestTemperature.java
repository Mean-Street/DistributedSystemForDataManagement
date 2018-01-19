package WeatherFinder.Requests;

public abstract class RequestTemperature extends Request {
    final private String topic = "temperature";

    @Override
    public String getTopic() {
        return topic;
    }
}
