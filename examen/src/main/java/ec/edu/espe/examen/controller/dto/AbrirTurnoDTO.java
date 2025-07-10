package ec.edu.espe.examen.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AbrirTurnoDTO {
    private String codigoCaja;
    private String codigoCajero;
    private List<DenominacionCantidadDTO> detalleInicial;
}