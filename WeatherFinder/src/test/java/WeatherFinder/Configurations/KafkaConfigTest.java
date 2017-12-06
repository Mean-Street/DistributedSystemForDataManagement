package WeatherFinder.Configurations;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KafkaConfigTest {

    @Test
    void whenConstructing() {
        Integer[] host = {0, 0, 0, 0};
        new KafkaConfig(host, 12);
    }

    @Test
    void whenToString() {
        Integer[] host = {127, 0, 0, 1};
        KafkaConfig config = new KafkaConfig(host, 12);
        assertEquals(config.toString(), "127.0.0.1:12");
    }

}