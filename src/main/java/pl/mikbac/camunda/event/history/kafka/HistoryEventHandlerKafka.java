package pl.mikbac.camunda.event.history.kafka;

import com.google.gson.GsonBuilder;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.camunda.bpm.engine.impl.history.event.HistoryEvent;
import org.camunda.bpm.engine.impl.history.handler.HistoryEventHandler;

import java.util.List;
import java.util.Properties;
import java.util.UUID;

/**
 * Created by MikBac on 07.06.2023
 */

public class HistoryEventHandlerKafka implements HistoryEventHandler {

    private final String kafkaTopic;
    private final KafkaProducer kafkaProducer;
    private final GsonBuilder gsonBuildr;

    public HistoryEventHandlerKafka(final String clientId,
                                    final String kafkaBrokers,
                                    final String kafkaTopic) {
        this.kafkaTopic = kafkaTopic;
        this.gsonBuildr = new GsonBuilder();
        Properties config = new Properties();
        config.put("client.id", clientId);
        config.put("bootstrap.servers", kafkaBrokers);
        config.put("acks", "all");
        config.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        config.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        kafkaProducer = new KafkaProducer<String, String>(config);
    }

    @Override
    public void handleEvent(final HistoryEvent historyEvent) {
        kafkaProducer.send(new ProducerRecord<>(kafkaTopic,
                UUID.randomUUID().toString(),
                gsonBuildr.create().toJson(historyEvent)));
    }

    @Override
    public void handleEvents(final List<HistoryEvent> historyEvents) {
        historyEvents.forEach(this::handleEvent);
    }
}
