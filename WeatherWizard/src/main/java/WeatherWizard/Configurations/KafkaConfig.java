package WeatherWizard.Configurations;

import java.util.Arrays;
import java.util.stream.Collectors;

public class KafkaConfig {
    private Integer[] host;
    private int port;

    public KafkaConfig(Integer[] host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public String toString() {
        return String.join(".", Arrays.stream(host)
                                               .map(Object::toString)
                                               .collect(Collectors.joining(".")))
                + ":" + port;
    }
}
