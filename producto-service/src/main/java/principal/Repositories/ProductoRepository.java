package principal.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import principal.Models.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
