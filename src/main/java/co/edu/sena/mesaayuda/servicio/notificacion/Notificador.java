package co.edu.sena.mesaayuda.servicio.notificacion;

import co.edu.sena.mesaayuda.modelo.Ticket;

public interface Notificador {
    void notificarCambioEstado(Ticket ticket, String estadoAnterior, String estadoNuevo);
    String obtenerCanal();
}
