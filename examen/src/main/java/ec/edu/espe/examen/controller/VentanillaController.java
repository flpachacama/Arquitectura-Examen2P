package ec.edu.espe.examen.controller;

import ec.edu.espe.examen.enums.TipoTransaccionEnum;
import ec.edu.espe.examen.model.DenominacionCantidad;
import ec.edu.espe.examen.model.TransaccionTurno;
import ec.edu.espe.examen.model.TurnoCaja;
import ec.edu.espe.examen.service.VentanillaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/ventanilla")
@Tag(name = "Ventanilla API", description = "Operaciones de turnos de caja")
public class VentanillaController {

    @Autowired
    private VentanillaService service;

    @PostMapping("/abrir-turno")
    @Operation(summary = "Abrir Turno")
    public TurnoCaja abrirTurno(@RequestParam String codigoCaja, @RequestParam String codigoCajero,
                                @RequestBody List<DenominacionCantidad> detalleInicial) {
        return service.iniciarTurno(codigoCaja, codigoCajero, detalleInicial);
    }

    @PostMapping("/transaccion")
    @Operation(summary = "Procesar Transaccion")
    public TransaccionTurno procesar(@RequestParam String codigoTurno,
                                     @RequestParam TipoTransaccionEnum tipo,
                                     @RequestBody List<DenominacionCantidad> detalles) {
        return service.procesarTransaccion(codigoTurno, tipo, detalles);
    }

    @PostMapping("/cerrar-turno")
    @Operation(summary = "Cerrar Turno")
    public TurnoCaja cerrar(@RequestParam String codigoTurno,
                            @RequestBody List<DenominacionCantidad> detalleFinal) {
        return service.cerrarTurno(codigoTurno, detalleFinal);
    }
}
