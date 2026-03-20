package principal.Configs;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import principal.Models.Producto;

@FeignClient(name = "producto-service")
public interface ProductoClient {
    @GetMapping("/api/v1/productos/{id}")
    Producto obtenerProducto(@PathVariable("id") Long id);
}
