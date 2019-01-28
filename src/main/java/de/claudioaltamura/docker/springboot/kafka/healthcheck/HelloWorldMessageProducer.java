package de.claudioaltamura.docker.springboot.kafka.healthcheck;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class HelloWorldMessageProducer {

  private static final Logger log = LoggerFactory.getLogger(HelloWorldMessageProducer.class);

  @Autowired
  private KafkaTemplate<String, HelloWorldMessage> kafkaTemplate;

  public void send(String topic, HelloWorldMessage helloWorldMessage) {
    log.info("Sending helloWorldMessag='{}' to topic='{}'", helloWorldMessage, topic);
    Message<HelloWorldMessage> message =
        MessageBuilder.withPayload(helloWorldMessage).setHeader(KafkaHeaders.TOPIC, topic).build();
    this.kafkaTemplate.send(message);
  }

  public void sendInDefault(HelloWorldMessage helloWorldMessage) {
    log.info("Sending helloWorldMessage='{}' to default topic", helloWorldMessage);
    this.kafkaTemplate.sendDefault(helloWorldMessage);
  }

}
