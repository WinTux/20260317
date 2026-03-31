package principal.Services;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import principal.Configs.KafkaOrdenEventProducer;
import principal.Configs.OrdenServicePublisher;
import principal.Configs.ProductoClient;
import principal.Models.Orden;
import principal.Models.Producto;
import principal.Repositories.OrdenRepository;
import principalcomunes.OrdenEvent;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdenService {
    @Autowired
    private OrdenRepository ordenRepository;
    @Autowired
    private RestTemplate restTemplate;
    private final ProductoClient productoClient;
    @Autowired
    private OrdenServicePublisher  ordenServicePublisher;
    @Autowired
    private KafkaOrdenEventProducer kafkaProducer;
    @CircuitBreaker(name = "productoServiceCB", fallbackMethod = "fallbackGetProducto")
    public Orden registrarOrden(Orden orden){
        // Llamada al microservicio de producto-service
        //String productoUrl = "http://localhost:8081/api/v1/productos/"+orden.getProductoId();
        //Producto producto = restTemplate.getForObject(productoUrl, Producto.class);

        //Usando feign y Eureka Server
        Producto producto = productoClient.obtenerProducto(orden.getProductoId());

        if(producto == null) throw new RuntimeException("Producto no encontrado");
        orden.setPrecioTotal(producto.getPrecio() * orden.getCantidad());
        Orden ordenActual = ordenRepository.save(orden);
        // Comunicación asíncrona con RabbitMQ
        ordenServicePublisher.publicarOrdenEvent(
                new OrdenEvent(ordenActual.getId(), ordenActual.getProductoId(), ordenActual.getCantidad())
        );
        // Comunicación asíncrona con Kafka
        kafkaProducer.enviarOrdenEvent(
                new OrdenEvent(ordenActual.getId(), ordenActual.getProductoId(), ordenActual.getCantidad())
        );
        return ordenActual;
    }
    public List<Orden> listarOrdenes(){
        return ordenRepository.findAll();
    }

    // Para Circuit Breaker
    public Orden fallbackGetProducto(Orden o, Throwable throwable){
        System.out.println("FALLBACK originado por: "+throwable.getMessage());
        Orden fallblackOrden  = new Orden();
        fallblackOrden.setProductoId(o.getProductoId());
        fallblackOrden.setCantidad(o.getCantidad());
        fallblackOrden.setPrecioTotal(-1.0); // O algún valor por defecto
        return fallblackOrden;
    }
}


