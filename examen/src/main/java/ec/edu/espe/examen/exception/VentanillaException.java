package ec.edu.espe.examen.exception;

public class VentanillaException extends RuntimeException {
    private final Integer codigoError;

    public VentanillaException(String mensaje, Integer codigoError) {
        super(mensaje);
        this.codigoError = codigoError;
    }

    @Override
    public String getMessage() {
        return "Código de error: " + this.codigoError + ", Mensaje: " + super.getMessage();
    }

    public Integer getCodigoError() {
        return codigoError;
    }
}