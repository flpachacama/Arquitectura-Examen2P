package ec.edu.espe.examen.model;

import ec.edu.espe.examen.enums.DenominacionBilleteEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DenominacionCantidad {
    private DenominacionBilleteEnum denominacion;
    private Integer cantidad;
}