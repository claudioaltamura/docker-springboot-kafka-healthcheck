[![Build Status](https://travis-ci.org/claudioaltamura/docker-springboot-kafka-healthcheck.svg?branch=master)](https://travis-ci.org/claudioaltamura/docker-springboot-kafka-healthcheck)

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

# docker-springboot-kafka-healthcheck
spring boot example with docker and kafka

## Building the example

Build the jar

	./gradlew clean build


Build docker image

	docker build --tag docker-springboot-kafka-healthcheck:latest .

## Running the example java -jar 

Start current kafka docker container

	docker-compose up


Test

	curl http://localhost:8080/helloworld


Checking the healthcheck endpoint

	curl http://localhost:8080/actuator/health | jq .
