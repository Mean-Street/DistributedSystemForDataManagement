package WeatherFinder.Configurations;

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

    public enum Topic {
        TEMPERATURE ("temperatures");

        private String name = "";
        Topic(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
