package principal.Configs;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import principalcomunes.OrdenEvent;

@Component
@RequiredArgsConstructor
public class OrdenServicePublisher {
    private final RabbitTemplate rabbitTemplate;
    public  void publicarOrdenEvent(OrdenEvent event){
        System.out.println("OrdenEvent a ser publicado: "+event);
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.ROUTING_KEY,
                event
        );
    }
}
