package ec.edu.espe.examen.controller;

import ec.edu.espe.examen.controller.dto.AbrirTurnoDTO;
import ec.edu.espe.examen.controller.dto.CerrarTurnoDTO;
import ec.edu.espe.examen.controller.dto.ProcesarTransaccionDTO;
import ec.edu.espe.examen.model.TransaccionTurno;
import ec.edu.espe.examen.model.TurnoCaja;
import ec.edu.espe.examen.service.VentanillaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/ventanilla")
@Tag(name = "Ventanilla API", description = "Operaciones de turnos de caja")
public class VentanillaController {

    private final VentanillaService service;

    public VentanillaController(VentanillaService service) {
        this.service = service;
    }

    @PostMapping("/abrir-turno")
    @Operation(summary = "Abrir Turno", description = "Permite al cajero abrir un turno con monto inicial.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Turno abierto exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "409", description = "Ya existe un turno abierto para el cajero y caja")
    })
    public ResponseEntity<TurnoCaja> abrirTurno(@RequestBody AbrirTurnoDTO dto) {
        log.info("Solicitud para abrir turno - caja: {}, cajero: {}", dto.getCodigoCaja(), dto.getCodigoCajero());
        TurnoCaja turnoCreado = service.abrirTurno(dto);
        return ResponseEntity.ok(turnoCreado);
    }

    @PostMapping("/transaccion")
    @Operation(summary = "Procesar Transacción", description = "Registra una transacción (depósito o retiro) en un turno abierto.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Transacción procesada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Turno no encontrado"),
            @ApiResponse(responseCode = "409", description = "Turno ya cerrado")
    })
    public ResponseEntity<TransaccionTurno> procesarTransaccion(@RequestBody ProcesarTransaccionDTO dto) {
        log.info("Solicitud para procesar transacción tipo {} para turno {}", dto.getTipo(), dto.getCodigoTurno());
        TransaccionTurno transaccion = service.procesarTransaccion(dto);
        return ResponseEntity.ok(transaccion);
    }

    @PostMapping("/cerrar-turno")
    @Operation(summary = "Cerrar Turno", description = "Cierra un turno realizando la validación final de billetes.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Turno cerrado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o inconsistencia en montos"),
            @ApiResponse(responseCode = "404", description = "Turno no encontrado"),
            @ApiResponse(responseCode = "409", description = "Turno ya cerrado")
    })
    public ResponseEntity<TurnoCaja> cerrarTurno(@RequestBody CerrarTurnoDTO dto) {
        log.info("Solicitud para cerrar turno {}", dto.getCodigoTurno());
        TurnoCaja turnoCerrado = service.cerrarTurno(dto);
        return ResponseEntity.ok(turnoCerrado);
    }
}
