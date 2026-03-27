package principalcomunes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdenEvent {
    private Long ordenId;
    private Long productoId;
    private Integer cantidad;
}
