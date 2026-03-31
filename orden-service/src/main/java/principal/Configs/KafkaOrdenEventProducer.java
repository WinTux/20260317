package principal.Configs;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import principalcomunes.OrdenEvent;

@Component
@RequiredArgsConstructor
public class KafkaOrdenEventProducer {
    private final KafkaTemplate<String, OrdenEvent> kafkaTemplate;
    public void enviarOrdenEvent(OrdenEvent ordenEvent) {
        kafkaTemplate.send("orden-event", ordenEvent);
        System.out.println("Enviando Orden Event a Kafka: " + ordenEvent);
    }
}
