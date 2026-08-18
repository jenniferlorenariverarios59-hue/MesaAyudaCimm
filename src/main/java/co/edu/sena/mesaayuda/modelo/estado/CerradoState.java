package co.edu.sena.mesaayuda.modelo.estado;

public class CerradoState implements EstadoTicket {

    @Override
    public EstadoTicket asignar() {
        throw new TransicionEstadoInvalidaException(nombre(), "asignar");
    }

    @Override
    public EstadoTicket iniciar() {
        throw new TransicionEstadoInvalidaException(nombre(), "iniciar");
    }

    @Override
    public EstadoTicket resolver() {
        throw new TransicionEstadoInvalidaException(nombre(), "resolver");
    }

    @Override
    public EstadoTicket cerrar() {
        throw new TransicionEstadoInvalidaException(nombre(), "cerrar (ya está cerrado)");
    }

    @Override
    public EstadoTicket reabrir() {
        throw new TransicionEstadoInvalidaException(nombre(), "reabrir");
    }

    @Override
    public EstadoTicket cancelar() {
        throw new TransicionEstadoInvalidaException(nombre(), "cancelar");
    }

    @Override
    public String nombre() {
        return "CERRADO";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CerradoState;
    }

    @Override
    public int hashCode() {
        return nombre().hashCode();
    }
}
