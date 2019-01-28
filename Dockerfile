FROM openjdk:8-jdk-alpine

COPY build/libs/docker-springboot-kafka-0.1.0.jar /usr/src/docker-springboot-kafka-0.1.0.jar

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /usr/src/docker-springboot-kafka-0.1.0.jar"]

EXPOSE 8080