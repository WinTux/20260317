package principal.Repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import principal.Models.Orden;

public interface OrdenRepository extends JpaRepository<Orden, Long> {
}
