package principal.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import principal.Models.Orden;
import principal.Models.Producto;
import principal.Repositories.OrdenRepository;

import java.util.List;

@Service
public class OrdenService {
    @Autowired
    private OrdenRepository ordenRepository;
    @Autowired
    private RestTemplate restTemplate;
    public Orden registrarOrden(Orden orden){
        // Llamada al microservicio de producto-service
        String productoUrl = "http://localhost:8081/api/v1/productos/"+orden.getProductoId();
        Producto producto = restTemplate.getForObject(productoUrl, Producto.class);
        if(producto == null) throw new RuntimeException("Producto no encontrado");
        orden.setPrecioTotal(producto.getPrecio() * orden.getCantidad());
        return ordenRepository.save(orden);
    }
    public List<Orden> listarOrdenes(){
        return ordenRepository.findAll();
    }
}


