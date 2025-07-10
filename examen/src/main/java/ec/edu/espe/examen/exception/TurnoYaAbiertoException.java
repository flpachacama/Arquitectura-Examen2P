package ec.edu.espe.examen.exception;

public class TurnoYaAbiertoException extends VentanillaException {
    public TurnoYaAbiertoException(String codigoCajero) {
        super("Ya existe un turno abierto para el cajero: " + codigoCajero, 1004);
    }
}