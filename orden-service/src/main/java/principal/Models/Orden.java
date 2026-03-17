package principal.Models;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="orden")
public class Orden {
    @Id
    @GeneratedValue
    private Long id;
    private Long productoId;
    private Integer cantidad;
    private Double precioTotal;
}

