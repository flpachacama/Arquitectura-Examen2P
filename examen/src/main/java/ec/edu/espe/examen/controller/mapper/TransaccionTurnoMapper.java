package ec.edu.espe.examen.controller.mapper;

import ec.edu.espe.examen.controller.dto.ProcesarTransaccionDTO;
import ec.edu.espe.examen.model.TransaccionTurno;
import ec.edu.espe.examen.model.TurnoCaja;

import java.time.LocalDateTime;
import java.util.UUID;

public class TransaccionTurnoMapper {

    public static TransaccionTurno fromDTO(ProcesarTransaccionDTO dto, TurnoCaja turno) {
        double montoTotal = dto.getDetalle().stream()
                .mapToDouble(d -> d.getDenominacion().getValor() * d.getCantidad()).sum();

        return TransaccionTurno.builder()
                .id(UUID.randomUUID().toString())
                .codigoTurno(dto.getCodigoTurno())
                .codigoCaja(turno.getCodigoCaja())
                .codigoCajero(turno.getCodigoCajero())
                .tipoTransaccion(dto.getTipo())
                .fecha(LocalDateTime.now())
                .montoTotal(montoTotal)
                .denominaciones(DenominacionCantidadMapper.toEntityList(dto.getDetalle()))
                .build();
    }
}