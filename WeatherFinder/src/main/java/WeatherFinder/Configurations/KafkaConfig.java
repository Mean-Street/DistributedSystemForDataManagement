package WeatherFinder.Configurations;

import java.util.Arrays;
import java.util.stream.Collectors;

public class KafkaConfig {
    private String host;
    private Integer port;
    private String topic;

    public KafkaConfig(String host, Integer port, String topic) {
        this.host = host;
        this.port = port;
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }

    public String getBroker() {
        return host + ":" + port.toString();
    }
}
