package ec.edu.espe.examen.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CerrarTurnoDTO {
    private String codigoTurno;
    private List<DenominacionCantidadDTO> detalleFinal;
}