package de.claudioaltamura.docker.springboot.kafka.healthcheck;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

  @Autowired
  private HelloWorldMessageProducer helloWorldMessageProducer;

  @GetMapping(value = {"/helloworld", "/helloworld/{name}"})
  public String greet(@PathVariable final Optional<String> name) {
    String message = "Hello World!";

    if (name.isPresent()) {
      message = "Hello " + name.get() + "!";
    }

    HelloWorldMessage helloWorldMessage = new HelloWorldMessage();
    helloWorldMessage.setMessage(message);
    helloWorldMessageProducer.sendInDefault(helloWorldMessage);

    return message;
  }

}