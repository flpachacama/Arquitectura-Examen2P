package ec.edu.espe.examen.exception;

public class MontoFinalInconsistenteException extends VentanillaException {
    public MontoFinalInconsistenteException(double esperado, double real) {
        super("Diferencia detectada al cerrar el turno. Monto esperado: " + esperado + ", monto declarado: " + real, 1003);
    }
}