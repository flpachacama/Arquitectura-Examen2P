package ec.edu.espe.examen.exception;

public class TurnoNoEncontradoException extends VentanillaException {
    public TurnoNoEncontradoException(String codigoTurno) {
        super("No se encontró el turno con código: " + codigoTurno, 1001);
    }
}