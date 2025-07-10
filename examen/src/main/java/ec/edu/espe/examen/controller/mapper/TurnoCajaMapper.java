package ec.edu.espe.examen.controller.mapper;

import ec.edu.espe.examen.controller.dto.AbrirTurnoDTO;
import ec.edu.espe.examen.model.TurnoCaja;

import java.time.LocalDateTime;
import java.util.UUID;

public class TurnoCajaMapper {

    public static TurnoCaja fromAbrirTurnoDTO(AbrirTurnoDTO dto) {
        String fechaFormateada = LocalDateTime.now().toLocalDate().toString().replace("-", "");
        String codigoTurno = dto.getCodigoCaja() + "-" + dto.getCodigoCajero() + "-" + fechaFormateada;

        double montoInicial = dto.getDetalleInicial().stream()
                .mapToDouble(d -> d.getDenominacion().getValor() * d.getCantidad()).sum();

        return TurnoCaja.builder()
                .id(UUID.randomUUID().toString())
                .codigoCaja(dto.getCodigoCaja())
                .codigoCajero(dto.getCodigoCajero())
                .codigoTurno(codigoTurno)
                .inicioTurno(LocalDateTime.now())
                .montoInicial(montoInicial)
                .estado("ABIERTO")
                .detalleInicial(DenominacionCantidadMapper.toEntityList(dto.getDetalleInicial()))
                .build();
    }

}