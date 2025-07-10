package ec.edu.espe.examen.service;

import ec.edu.espe.examen.controller.mapper.DenominacionCantidadMapper;
import ec.edu.espe.examen.controller.mapper.TransaccionTurnoMapper;
import ec.edu.espe.examen.controller.mapper.TurnoCajaMapper;
import ec.edu.espe.examen.enums.TipoTransaccionEnum;
import ec.edu.espe.examen.exception.*;
import ec.edu.espe.examen.model.*;
import ec.edu.espe.examen.repository.*;
import ec.edu.espe.examen.controller.dto.*;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class VentanillaService {

    private final TurnoCajaRepository turnoRepo;
    private final TransaccionTurnoRepository transaccionRepo;

    public VentanillaService(TurnoCajaRepository turnoRepo, TransaccionTurnoRepository transaccionRepo) {
        this.turnoRepo = turnoRepo;
        this.transaccionRepo = transaccionRepo;
    }

    public TurnoCaja abrirTurno(AbrirTurnoDTO dto) {
        log.info("Intentando abrir turno para caja={} cajero={}", dto.getCodigoCaja(), dto.getCodigoCajero());

        if (dto.getDetalleInicial() == null || dto.getDetalleInicial().isEmpty()) {
            throw new VentanillaException("Debe especificar al menos una denominación al abrir el turno.", 2001);
        }

        boolean yaAbierto = turnoRepo
                .findByCodigoCajaAndCodigoCajeroAndEstado(dto.getCodigoCaja(), dto.getCodigoCajero(), "ABIERTO")
                .isPresent();

        if (yaAbierto) {
            throw new TurnoYaAbiertoException(dto.getCodigoCajero());
        }

        dto.getDetalleInicial().forEach(d -> {
            if (d.getCantidad() <= 0) {
                throw new VentanillaException("La cantidad de billetes debe ser mayor a cero. Denominación: " + d.getDenominacion(), 2002);
            }
        });

        TurnoCaja turno = TurnoCajaMapper.fromAbrirTurnoDTO(dto);
        TurnoCaja saved = turnoRepo.save(turno);

        log.info("Turno abierto: {}", saved.getCodigoTurno());
        return saved;
    }

    public TransaccionTurno procesarTransaccion(ProcesarTransaccionDTO dto) {
        log.info("Procesando transacción {} para turno {}", dto.getTipo(), dto.getCodigoTurno());

        if (dto.getDetalle() == null || dto.getDetalle().isEmpty()) {
            throw new VentanillaException("El detalle de la transacción no puede estar vacío.", 2003);
        }

        dto.getDetalle().forEach(d -> {
            if (d.getCantidad() <= 0) {
                throw new VentanillaException("La cantidad de billetes debe ser mayor a cero. Denominación: " + d.getDenominacion(), 2004);
            }
        });

        TurnoCaja turno = turnoRepo.findByCodigoTurno(dto.getCodigoTurno())
                .orElseThrow(() -> new TurnoNoEncontradoException(dto.getCodigoTurno()));

        if ("CERRADO".equalsIgnoreCase(turno.getEstado())) {
            throw new TurnoYaCerradoException(dto.getCodigoTurno());
        }

        TransaccionTurno trx = TransaccionTurnoMapper.fromDTO(dto, turno);
        TransaccionTurno saved = transaccionRepo.save(trx);

        log.info("Transacción registrada: ID={}, monto=${}", saved.getId(), saved.getMontoTotal());
        return saved;
    }

    public TurnoCaja cerrarTurno(CerrarTurnoDTO dto) {
        log.info("Cerrando turno {}", dto.getCodigoTurno());

        if (dto.getDetalleFinal() == null || dto.getDetalleFinal().isEmpty()) {
            throw new VentanillaException("Debe ingresar el detalle final de billetes para cerrar el turno.", 2005);
        }

        dto.getDetalleFinal().forEach(d -> {
            if (d.getCantidad() < 0) {
                throw new VentanillaException("La cantidad de billetes no puede ser negativa. Denominación: " + d.getDenominacion(), 2006);
            }
        });

        TurnoCaja turno = turnoRepo.findByCodigoTurno(dto.getCodigoTurno())
                .orElseThrow(() -> new TurnoNoEncontradoException(dto.getCodigoTurno()));

        if ("CERRADO".equalsIgnoreCase(turno.getEstado())) {
            throw new TurnoYaCerradoException(dto.getCodigoTurno());
        }

        List<DenominacionCantidad> detalleFinal = DenominacionCantidadMapper.toEntityList(dto.getDetalleFinal());

        double montoFinal = detalleFinal.stream()
                .mapToDouble(d -> d.getDenominacion().getValor() * d.getCantidad())
                .sum();

        List<TransaccionTurno> transacciones = transaccionRepo.findByCodigoTurno(dto.getCodigoTurno());

        double montoEsperado = turno.getMontoInicial();
        for (TransaccionTurno trx : transacciones) {
            if (trx.getTipoTransaccion() == TipoTransaccionEnum.DEPOSITO) {
                montoEsperado += trx.getMontoTotal();
            } else if (trx.getTipoTransaccion() == TipoTransaccionEnum.RETIRO) {
                montoEsperado -= trx.getMontoTotal();
            }
        }

        if (Math.abs(montoEsperado - montoFinal) > 0.01) {
            log.error("Inconsistencia al cerrar turno {}. Esperado: {}, Declarado: {}", dto.getCodigoTurno(), montoEsperado, montoFinal);
            throw new MontoFinalInconsistenteException(montoEsperado, montoFinal);
        }

        turno.setDetalleFinal(detalleFinal);
        turno.setMontoFinal(montoFinal);
        turno.setFinTurno(LocalDateTime.now());
        turno.setEstado("CERRADO");

        TurnoCaja cerrado = turnoRepo.save(turno);
        log.info("Turno cerrado correctamente: {}", cerrado.getCodigoTurno());
        return cerrado;
    }
}
