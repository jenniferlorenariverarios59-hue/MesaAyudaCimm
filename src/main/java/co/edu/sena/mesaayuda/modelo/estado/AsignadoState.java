package co.edu.sena.mesaayuda.modelo.estado;

public class AsignadoState implements EstadoTicket {

    @Override
    public EstadoTicket asignar() {
        // Reasignación directa o mantener asignado
        return this;
    }

    @Override
    public EstadoTicket iniciar() {
        return new EnProcesoState();
    }

    @Override
    public EstadoTicket resolver() {
        throw new TransicionEstadoInvalidaException(nombre(), "resolver (primero inicie atención)");
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
        return "ASIGNADO";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof AsignadoState;
    }

    @Override
    public int hashCode() {
        return nombre().hashCode();
    }
}
