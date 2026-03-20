package principal.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import principal.Configs.ProductoClient;
import principal.Models.Orden;
import principal.Models.Producto;
import principal.Repositories.OrdenRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdenService {
    @Autowired
    private OrdenRepository ordenRepository;
    @Autowired
    private RestTemplate restTemplate;
    private final ProductoClient productoClient;
    public Orden registrarOrden(Orden orden){
        // Llamada al microservicio de producto-service
        //String productoUrl = "http://localhost:8081/api/v1/productos/"+orden.getProductoId();
        //Producto producto = restTemplate.getForObject(productoUrl, Producto.class);

        //Usando feign y Eureka Server
        Producto producto = productoClient.obtenerProducto(orden.getProductoId());

        if(producto == null) throw new RuntimeException("Producto no encontrado");
        orden.setPrecioTotal(producto.getPrecio() * orden.getCantidad());
        return ordenRepository.save(orden);
    }
    public List<Orden> listarOrdenes(){
        return ordenRepository.findAll();
    }
}


