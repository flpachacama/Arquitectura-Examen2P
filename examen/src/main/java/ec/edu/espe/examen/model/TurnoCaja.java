package ec.edu.espe.examen.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("turnos")
public class TurnoCaja {
    @Id
    private String id;
    private String codigoCaja;
    private String codigoCajero;
    private String codigoTurno;
    private LocalDateTime inicioTurno;
    private Double montoInicial;
    private LocalDateTime finTurno;
    private Double montoFinal;
    private String estado; // ABIERTO o CERRADO
    private List<DenominacionCantidad> detalleInicial;
    private List<DenominacionCantidad> detalleFinal;
}
