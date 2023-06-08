# Plugin for Camunda Engine to dump Event History directly to Kafka

A simple Camunda Engine Plugin POC to send history events from Camunda via a plugin to a topic in Kafka.

![img-1.png](img%2Fimg-1.png)

![img-2.png](img%2Fimg-2.png)

## Config

To configure use the [bpm-platform.xml](camunda-config%2Fbpm-platform.xml) file.

* kafkaBrokers - Kafka brokers addresses e.g. `bitnami-kafka:9094,bitnami-kafka-2:9094`.
* kafkaTopic - Kafka topic.
* clientId - Client ID.

e.g.

```xml
    <!-- Plugin for Camunda Engine to dump Event History directly to Kafka -->
<plugin>
    <class>pl.mikbac.camunda.event.history.kafka.ProcessEnginePluginKafka</class>
    <properties>
        <property name="kafkaBrokers">bitnami-kafka:9094</property>
        <property name="kafkaTopic">camunda-events</property>
        <property name="clientId">1</property>
    </properties>
</plugin>
```

## Building

**You should use Java version 11 to build.**

```shell
gradlew fatJar
```

## Running

**Can be executed only after the jar file is built.**

```shell
docker compose -f ./docker/camunda-and-kafka.yaml up
```

Services:

| Address                        | Service               |
|--------------------------------|-----------------------|
| localhost:9192                 | Kafka External broker | 
| bitnami-kafka:9094             | Kafka Internal broker | 
| http://localhost:9080          | Kouncil UI            | 
| http://localhost:8080/camunda/ | Camunda UI            | 
