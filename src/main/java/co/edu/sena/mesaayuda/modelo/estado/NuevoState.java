package co.edu.sena.mesaayuda.modelo.estado;

public class NuevoState implements EstadoTicket {

    @Override
    public EstadoTicket asignar() {
        return new AsignadoState();
    }

    @Override
    public EstadoTicket iniciar() {
        throw new TransicionEstadoInvalidaException(nombre(), "iniciar atención");
    }

    @Override
    public EstadoTicket resolver() {
        throw new TransicionEstadoInvalidaException(nombre(), "resolver");
    }

    @Override
    public EstadoTicket cerrar() {
        throw new TransicionEstadoInvalidaException(nombre(), "cerrar");
    }

    @Override
    public EstadoTicket reabrir() {
        throw new TransicionEstadoInvalidaException(nombre(), "reabrir");
    }

    @Override
    public EstadoTicket cancelar() {
        return new CanceladoState();
    }

    @Override
    public String nombre() {
        return "NUEVO";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof NuevoState;
    }

    @Override
    public int hashCode() {
        return nombre().hashCode();
    }
}
