package co.edu.sena.mesaayuda.modelo.estado;

public class EnProcesoState implements EstadoTicket {

    @Override
    public EstadoTicket asignar() {
        // Permitir reasignación manteniendo EN_PROCESO
        return this;
    }

    @Override
    public EstadoTicket iniciar() {
        throw new TransicionEstadoInvalidaException(nombre(), "iniciar (ya está en proceso)");
    }

    @Override
    public EstadoTicket resolver() {
        return new ResueltoState();
    }

    @Override
    public EstadoTicket cerrar() {
        throw new TransicionEstadoInvalidaException(nombre(), "cerrar (debe resolverse primero)");
    }

    @Override
    public EstadoTicket reabrir() {
        throw new TransicionEstadoInvalidaException(nombre(), "reabrir (ya está en proceso)");
    }

    @Override
    public EstadoTicket cancelar() {
        return new CanceladoState();
    }

    @Override
    public String nombre() {
        return "EN_PROCESO";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof EnProcesoState;
    }

    @Override
    public int hashCode() {
        return nombre().hashCode();
    }
}
