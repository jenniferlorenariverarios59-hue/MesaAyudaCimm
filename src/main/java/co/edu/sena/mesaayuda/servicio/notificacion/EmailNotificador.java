package co.edu.sena.mesaayuda.servicio.notificacion;

import co.edu.sena.mesaayuda.modelo.Ticket;

import java.util.logging.Logger;

public class EmailNotificador implements Notificador {

    private static final Logger LOGGER = Logger.getLogger(EmailNotificador.class.getName());

    @Override
    public void notificarCambioEstado(Ticket ticket, String estadoAnterior, String estadoNuevo) {
        if (ticket != null && ticket.getSolicitante() != null) {
            String mensaje = String.format("[EMAIL a %s]: Estimado(a) %s, su ticket #%d '%s' ha cambiado de estado de [%s] a [%s].",
                    ticket.getSolicitante().getCorreo(),
                    ticket.getSolicitante().getNombre(),
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
        return "Correo Electrónico";
    }
}
