package principal.Configs;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE = "orden.queue";
    public static final String EXCHANGE = "orden.exchange";
    public static final String ROUTING_KEY = "orden.creado";
// REVISAR PORQUE LO RESCATÉ DE CHATGPT
    // Cola
    @Bean
    public Queue queue() {
        return new Queue(QUEUE, true);
    }

    // Exchange
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    // Binding
    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(ROUTING_KEY);
    }

    // Template para enviar mensajes
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }
}