# Education System

## Architecture
- Distributed system (Zookeeper, Kafka, Spring-cloud)
- Load Balancer, Gateway, Circuit breaker, Pub/Sub
- MVC, Repository pattern, Builder pattern

## Insight tech
- Redis
- NoSQL

## Docker
- Thanks elkozmon for the opensource Zookeeper UI.
- You guys can find him here [Github-repo](https://github.com/elkozmon/zoonavigator)
- Easily run this project as follows:<br>
``` docker compose up -d ```
- And use this to view the UI at <i>localhost:9000</i>  [Zookeeper-UI](http://localhost:9000)

## Kafka Architecture

![Kafka](resources/images/KafkaClusterArchitecture.png)

- Use Kafka console producer for bitnami/image (Alternative, we can use [Offset Explorer](https://www.kafkatool.com/)) <br>
```docker exec -it {container-id} /bin/kafka-console-producer --bootstrap-server kafka:9092 --topic {topic-name}```