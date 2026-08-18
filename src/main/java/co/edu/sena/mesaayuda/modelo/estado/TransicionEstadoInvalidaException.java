package co.edu.sena.mesaayuda.modelo.estado;

public class TransicionEstadoInvalidaException extends RuntimeException {
    public TransicionEstadoInvalidaException(String estadoActual, String accionSolicitada) {
        super("Transición inválida: No se puede '" + accionSolicitada + "' cuando el ticket está en estado " + estadoActual + ".");
    }
}
