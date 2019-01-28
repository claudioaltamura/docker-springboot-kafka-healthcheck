[![Build Status](https://travis-ci.org/claudioaltamura/docker-springboot-kafka-healthcheck.svg?branch=master)](https://travis-ci.org/claudioaltamura/docker-springboot-kafka-healthcheck)

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

# docker-springboot-kafka-healthcheck
spring boot example with docker and kafka

## Building the example
Build docker image

	docker build --tag docker-springboot-kafka-healthcheck:latest .

## Running the example java -jar 

Start current kafka docker container

	docker-compose up

Get the current IP address from kafka

	docker inspect docker-springboot-kafka-healthcheck_helloworld_1 | grep IP
            "LinkLocalIPv6Address": "",
            "LinkLocalIPv6PrefixLen": 0,
            "SecondaryIPAddresses": null,
            "SecondaryIPv6Addresses": null,
            "GlobalIPv6Address": "",
            "GlobalIPv6PrefixLen": 0,
            "IPAddress": "",
            "IPPrefixLen": 0,
            "IPv6Gateway": "",
                    "IPAMConfig": null,
                    "IPAddress": "192.168.112.3",
                    "IPPrefixLen": 20,
                    "IPv6Gateway": "",
                    "GlobalIPv6Address": "",
                    "GlobalIPv6PrefixLen": 0,

Test

	curl http://192.168.112.3:8080/helloworld


Checking the healthcheck endpoint

	curl http://192.168.112.3:8080/actuator/health | jq .
