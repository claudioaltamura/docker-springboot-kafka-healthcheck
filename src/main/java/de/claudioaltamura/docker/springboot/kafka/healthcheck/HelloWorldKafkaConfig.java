package de.claudioaltamura.docker.springboot.kafka.healthcheck;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

@Configuration
public class HelloWorldKafkaConfig {

  @Value("${kafka.healthcheck.request.timeoutMs}")
  private long requestTimeoutMs;

  @Autowired
  private KafkaAdmin kafkaAdmin;

  @Bean
  public HealthIndicator kafkaHealthIndicator() {
    return new KafkaHealthIndicator(kafkaAdmin, requestTimeoutMs);
  }

}
