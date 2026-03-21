package principal.Models;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="orden")
public class Orden {
    @Id
    @GeneratedValue
    private Long id;
    private Long productoId;
    private Integer cantidad;
    private Double precioTotal;
}

