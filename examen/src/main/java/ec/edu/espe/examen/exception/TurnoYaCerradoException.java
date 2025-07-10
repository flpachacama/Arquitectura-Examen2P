package ec.edu.espe.examen.exception;

public class TurnoYaCerradoException extends VentanillaException {
    public TurnoYaCerradoException(String codigoTurno) {
        super("El turno con código " + codigoTurno + " ya se encuentra cerrado.", 1002);
    }
}