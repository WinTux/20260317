package principal.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import principal.Models.Producto;
import principal.Repositories.ProductoRepository;

import java.util.List;

@Service
public class ProductoService {
    @Autowired
    private ProductoRepository productoRepository;
    public Producto guardar(Producto producto) {
        return productoRepository.save(producto);
    }
    public Producto getById(Long id) {
        //return productoRepository.findById(id).get();
        return productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }
    public List<Producto> getTodos() {
        return productoRepository.findAll();
    }
}
