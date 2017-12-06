package WeatherFinder.Requests;

import WeatherFinder.Configurations.KafkaConfig;

public abstract class RequestTemperature extends Request {
    final private KafkaConfig.Topic topic = KafkaConfig.Topic.TEMPERATURE;

    @Override
    public KafkaConfig.Topic getTopic() {
        return topic;
    }
}