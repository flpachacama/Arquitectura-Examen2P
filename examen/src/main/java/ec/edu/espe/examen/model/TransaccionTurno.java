package ec.edu.espe.examen.model;

import ec.edu.espe.examen.enums.TipoTransaccionEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("transacciones")
public class TransaccionTurno {
    @Id
    private String id;
    private String codigoCaja;
    private String codigoCajero;
    private String codigoTurno;
    private LocalDateTime fecha;
    private TipoTransaccionEnum tipoTransaccion; // INICIO, DEPOSITO, RETIRO, CIERRE
    private Double montoTotal;
    private List<DenominacionCantidad> denominaciones;
}