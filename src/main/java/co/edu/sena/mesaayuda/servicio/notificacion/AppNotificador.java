package co.edu.sena.mesaayuda.servicio.notificacion;

import co.edu.sena.mesaayuda.modelo.Ticket;
import co.edu.sena.mesaayuda.modelo.Usuario;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class AppNotificador implements Notificador {

    private static final Logger LOGGER =
            Logger.getLogger(AppNotificador.class.getName());

    private final Map<Long, List<String>> historialNotificaciones =
            Collections.synchronizedMap(new HashMap<>());

    @Override
    public void notificarCambioEstado(
            Ticket ticket,
            String estadoAnterior,
            String estadoNuevo) {

        if (ticket == null) {
            return;
        }

        String registro = String.format(
                "[NOTIFICACIÓN EN APP]: El ticket #%d '%s' ha cambiado a estado %s.",
                ticket.getId(),
                ticket.getTitulo(),
                estadoNuevo
        );

        // Notificar al solicitante
        agregarNotificacion(ticket.getSolicitante(), registro);

        // Notificar al agente asignado
        agregarNotificacion(ticket.getAgente(), registro);

        LOGGER.info(registro);
    }

    private void agregarNotificacion(Usuario usuario, String mensaje) {

        if (usuario == null || usuario.getId() == null) {
            return;
        }

        historialNotificaciones
                .computeIfAbsent(
                        usuario.getId(),
                        id -> Collections.synchronizedList(new ArrayList<>())
                )
                .add(mensaje);
    }

    @Override
    public String obtenerCanal() {
        return "Notificación Interna en App";
    }

    public List<String> getHistorialNotificaciones(Long usuarioId) {

        if (usuarioId == null) {
            return new ArrayList<>();
        }

        return new ArrayList<>(
                historialNotificaciones.getOrDefault(
                        usuarioId,
                        Collections.emptyList()
                )
        );
    }
}
