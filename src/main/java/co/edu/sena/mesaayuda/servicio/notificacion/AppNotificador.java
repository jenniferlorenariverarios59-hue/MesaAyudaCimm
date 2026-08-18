package co.edu.sena.mesaayuda.servicio.notificacion;

import co.edu.sena.mesaayuda.modelo.Ticket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class AppNotificador implements Notificador {

    private static final Logger LOGGER = Logger.getLogger(AppNotificador.class.getName());
    private final List<String> historialNotificaciones = Collections.synchronizedList(new ArrayList<>());

    @Override
    public void notificarCambioEstado(Ticket ticket, String estadoAnterior, String estadoNuevo) {
        if (ticket != null && ticket.getSolicitante() != null) {
            String registro = String.format("[NOTIFICACIÓN EN APP]: El ticket #%d '%s' ha cambiado a estado %s.",
                    ticket.getId(), ticket.getTitulo(), estadoNuevo);
            historialNotificaciones.add(registro);
            LOGGER.info(registro);
            System.out.println(registro);
        }
    }

    @Override
    public String obtenerCanal() {
        return "Notificación Interna en App";
    }

    public List<String> getHistorialNotificaciones() {
        return new ArrayList<>(historialNotificaciones);
    }
}
