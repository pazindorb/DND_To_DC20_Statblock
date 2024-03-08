package com.pazindorb.statblockConverter.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

import static org.apache.kafka.clients.admin.AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG;

@Configuration
public class KafkaTopicConfig {

    public static final String DND_PARSER = "dnd-parser";
    public static final String TO_DC20_CONVERTER = "to-dc20-converter";

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic dndStatblockParser() {
        return new NewTopic(DND_PARSER, 1, (short) 1);
    }

    @Bean
    public NewTopic toDc20Converter() {
        return new NewTopic(TO_DC20_CONVERTER, 1, (short) 1);
    }
}
