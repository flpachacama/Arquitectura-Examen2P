package ec.edu.espe.examen.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcesarTransaccionDTO {
    private String codigoTurno;
    private String tipo;
    private List<DenominacionCantidadDTO> detalle;
}