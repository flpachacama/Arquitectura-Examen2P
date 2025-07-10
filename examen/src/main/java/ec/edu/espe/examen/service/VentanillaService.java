package ec.edu.espe.examen.service;

import ec.edu.espe.examen.enums.TipoTransaccionEnum;
import ec.edu.espe.examen.model.DenominacionCantidad;
import ec.edu.espe.examen.model.TransaccionTurno;
import ec.edu.espe.examen.model.TurnoCaja;
import ec.edu.espe.examen.repository.TransaccionTurnoRepository;
import ec.edu.espe.examen.repository.TurnoCajaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VentanillaService {
    @Autowired
    private TurnoCajaRepository turnoRepo;
    @Autowired
    private TransaccionTurnoRepository transaccionRepo;

    public TurnoCaja iniciarTurno(String codigoCaja, String codigoCajero, List<DenominacionCantidad> detalleInicial) {
        String codigoTurno = codigoCaja + codigoCajero + LocalDateTime.now().toLocalDate().toString().replace("-", "");
        double montoInicial = detalleInicial.stream()
                .mapToDouble(d -> d.getDenominacion().getValor() * d.getCantidad()).sum();
        TurnoCaja turno = TurnoCaja.builder()
                .codigoCaja(codigoCaja)
                .codigoCajero(codigoCajero)
                .codigoTurno(codigoTurno)
                .inicioTurno(LocalDateTime.now())
                .montoInicial(montoInicial)
                .estado("ABIERTO")
                .detalleInicial(detalleInicial)
                .build();
        return turnoRepo.save(turno);
    }

    public TransaccionTurno procesarTransaccion(String codigoTurno, TipoTransaccionEnum tipo, List<DenominacionCantidad> detalles) {
        double monto = detalles.stream()
                .mapToDouble(d -> d.getDenominacion().getValor() * d.getCantidad()).sum();
        TurnoCaja turno = turnoRepo.findByCodigoTurno(codigoTurno).orElseThrow();
        TransaccionTurno trx = TransaccionTurno.builder()
                .codigoTurno(codigoTurno)
                .codigoCaja(turno.getCodigoCaja())
                .codigoCajero(turno.getCodigoCajero())
                .tipoTransaccion(tipo)
                .fecha(LocalDateTime.now())
                .montoTotal(monto)
                .denominaciones(detalles)
                .build();
        return transaccionRepo.save(trx);
    }

    public TurnoCaja cerrarTurno(String codigoTurno, List<DenominacionCantidad> detalleFinal) {
        TurnoCaja turno = turnoRepo.findByCodigoTurno(codigoTurno).orElseThrow();
        double montoFinal = detalleFinal.stream()
                .mapToDouble(d -> d.getDenominacion().getValor() * d.getCantidad()).sum();
        List<TransaccionTurno> transacciones = transaccionRepo.findByCodigoTurno(codigoTurno);
        double montoEsperado = turno.getMontoInicial();
        for (TransaccionTurno trx : transacciones) {
            montoEsperado += trx.getTipoTransaccion() == TipoTransaccionEnum.DEPOSITO ? trx.getMontoTotal() : -trx.getMontoTotal();
        }
        if (Math.abs(montoEsperado - montoFinal) > 0.01) {
            System.out.println("[ALERTA] Diferencia de monto al cerrar turno.");
        }
        turno.setMontoFinal(montoFinal);
        turno.setFinTurno(LocalDateTime.now());
        turno.setDetalleFinal(detalleFinal);
        turno.setEstado("CERRADO");
        return turnoRepo.save(turno);
    }
}