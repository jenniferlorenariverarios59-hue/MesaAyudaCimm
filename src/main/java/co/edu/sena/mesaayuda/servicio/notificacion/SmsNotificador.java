package co.edu.sena.mesaayuda.servicio.notificacion;

import co.edu.sena.mesaayuda.modelo.Ticket;

import java.util.logging.Logger;

public class SmsNotificador implements Notificador {

    private static final Logger LOGGER = Logger.getLogger(SmsNotificador.class.getName());

    @Override
    public void notificarCambioEstado(Ticket ticket, String estadoAnterior, String estadoNuevo) {
        if (ticket != null && ticket.getSolicitante() != null) {
            String mensaje = String.format("[SMS]: Ticket #%d '%s' paso de %s a %s.",
                    ticket.getId(),
                    ticket.getTitulo(),
                    estadoAnterior,
                    estadoNuevo);
            LOGGER.info(mensaje);
            System.out.println(mensaje);
        }
    }

    @Override
    public String obtenerCanal() {
        return "Mensaje SMS";
    }
}
