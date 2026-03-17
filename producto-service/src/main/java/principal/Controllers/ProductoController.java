package principal.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import principal.Models.Producto;
import principal.Services.ProductoService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/productos")
public class ProductoController {
    @Autowired
    private ProductoService productoService;
    @PostMapping
    public Producto crear(@RequestBody Producto producto){
        return productoService.guardar(producto);
    }
    @GetMapping("/{id}")
    public Producto getPorId(@PathVariable Long id){
        return productoService.getById(id);
    }
    @GetMapping
    public List<Producto> getTodos(){
        return productoService.getTodos();
    }
}
