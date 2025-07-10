package ec.edu.espe.examen.controller.dto;

import ec.edu.espe.examen.enums.DenominacionBilleteEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DenominacionCantidadDTO {
    private DenominacionBilleteEnum denominacion;
    private int cantidad;
}