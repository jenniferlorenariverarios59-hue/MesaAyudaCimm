package co.edu.sena.mesaayuda.modelo.estado;

public class ResueltoState implements EstadoTicket {

    @Override
    public EstadoTicket asignar() {
        throw new TransicionEstadoInvalidaException(nombre(), "asignar agente a un ticket ya resuelto");
    }

    @Override
    public EstadoTicket iniciar() {
        throw new TransicionEstadoInvalidaException(nombre(), "iniciar atención");
    }

    @Override
    public EstadoTicket resolver() {
        throw new TransicionEstadoInvalidaException(nombre(), "resolver (ya está resuelto)");
    }

    @Override
    public EstadoTicket cerrar() {
        return new CerradoState();
    }

    @Override
    public EstadoTicket reabrir() {
        return new EnProcesoState();
    }

    @Override
    public EstadoTicket cancelar() {
        return new CanceladoState();
    }

    @Override
    public String nombre() {
        return "RESUELTO";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ResueltoState;
    }

    @Override
    public int hashCode() {
        return nombre().hashCode();
    }
}
