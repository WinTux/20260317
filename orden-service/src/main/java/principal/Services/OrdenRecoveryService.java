package principal.Services;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import principal.Configs.ProductoClient;
import principal.Models.Orden;
import principal.Models.Producto;
import principal.Repositories.OrdenRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdenRecoveryService {
    @Autowired
    private OrdenRepository ordenRepository;
    private final ProductoClient productoClient;
    public void recuperOrdenesPendientes(){
        System.out.println("Recuperando ordenes pendientes desde la base de datos...");
        List<Orden> ordenesPendientes = ordenRepository.findByPrecioTotal(-1.0);
        for(Orden orden :  ordenesPendientes){
            System.out.println("Recuperando la orden"+orden.getId()+" para su verificación...");
            try{
                Producto producto = productoClient.obtenerProducto(orden.getProductoId());
                orden.setPrecioTotal(producto.getPrecio() * orden.getCantidad());
                ordenRepository.save(orden);
            }catch(FeignException feignException){
                ordenRepository.delete(orden);
                System.out.printf("Producto %d no existe, se elimina de la DDBB orden-service", orden.getProductoId());
            }
        }
    }
}
