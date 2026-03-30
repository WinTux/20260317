package principal.Configs;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import principalcomunes.OrdenEvent;

@Component
public class OrdenEventListener {
    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void handleOrdenEvent(OrdenEvent event){
        System.out.println("Orden recibida por RabbitMQ: "+event);
        //Acá se implementa la lógica para utilizar al objeto de tipo OrdenEvent como se necesite
    }
}
