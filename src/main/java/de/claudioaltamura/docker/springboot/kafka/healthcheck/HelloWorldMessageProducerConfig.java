package de.claudioaltamura.docker.springboot.kafka.healthcheck;

import java.util.Map;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;

@Configuration
public class HelloWorldMessageProducerConfig {

  @Value("${helloworld.kafkaServer}")
  private String bootstrapServers;

  @Value("${helloworld.defaultTopic}")
  private String defaultTopic;

  @Bean
  public Map<String, Object> producerConfigs() {
    Map<String, Object> props = Maps.newHashMap();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

    return props;
  }

  @Bean
  public ProducerFactory<String, HelloWorldMessage> producerFactory() {
    ObjectMapper mapper = new ObjectMapper();
    return new DefaultKafkaProducerFactory<>(producerConfigs(), new StringSerializer(),
        new JsonSerializer<>(mapper));
  }

  @Bean
  public KafkaTemplate<String, HelloWorldMessage> notificationKafkaTemplate() {
    KafkaTemplate<String, HelloWorldMessage> template = new KafkaTemplate<>(producerFactory());
    template.setDefaultTopic(defaultTopic);
    return template;
  }

}
