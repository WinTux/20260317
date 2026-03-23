package principal.Repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import principal.Models.Orden;

import java.util.List;

public interface OrdenRepository extends JpaRepository<Orden, Long> {
    List<Orden> findByPrecioTotal(Double total);
}
