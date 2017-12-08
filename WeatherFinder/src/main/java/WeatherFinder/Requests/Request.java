package WeatherFinder.Requests;

import akka.http.javadsl.model.HttpRequest;

public abstract class Request {
    public abstract HttpRequest create();

    public abstract Location getLocation();

    public abstract String getTopic();
}
