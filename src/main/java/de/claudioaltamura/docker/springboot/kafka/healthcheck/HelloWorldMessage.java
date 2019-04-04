package de.claudioaltamura.docker.springboot.kafka.healthcheck;

public class HelloWorldMessage {

  private long timestamp;

  private String message;

  public long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public String toString() {
    return "HelloWorldMessage [timestamp=" + timestamp + ", message=" + message + "]";
  }

}
