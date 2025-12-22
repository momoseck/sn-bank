package sn.bank.transfertservice.config;

import sn.bank.common.event.TransferCompletedEvent;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
public class KafkaConfig {
    @Bean
    public ProducerFactory<String, TransferCompletedEvent> producerFactory(
            @Value("${spring.kafka.bootstrap-servers}") String bootstrap) {

        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrap);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public KafkaTemplate<String, TransferCompletedEvent> kafkaTemplate(
            ProducerFactory<String, TransferCompletedEvent> pf) {
        return new KafkaTemplate<>(pf);
    }
}

