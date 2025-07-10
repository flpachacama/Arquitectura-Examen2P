package ec.edu.espe.examen.controller.dto;

import ec.edu.espe.examen.enums.TipoTransaccionEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcesarTransaccionDTO {
    private String codigoTurno;
    private TipoTransaccionEnum tipo;
    private List<DenominacionCantidadDTO> detalle;
}