package co.edu.sena.mesaayuda.servicio.notificacion;

import co.edu.sena.mesaayuda.modelo.Ticket;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MultiNotificador implements Notificador {

    private final List<Notificador> notificadores = new ArrayList<>();

    public MultiNotificador(List<Notificador> notificadores) {
        if (notificadores != null) {
            this.notificadores.addAll(notificadores);
        }
    }

    public void agregarNotificador(Notificador notificador) {
        if (notificador != null) {
            notificadores.add(notificador);
        }
    }

    @Override
    public void notificarCambioEstado(Ticket ticket, String estadoAnterior, String estadoNuevo) {
        for (Notificador notificador : notificadores) {
            try {
                notificador.notificarCambioEstado(ticket, estadoAnterior, estadoNuevo);
            } catch (Exception e) {
                System.err.println("Error enviando notificación por " + notificador.obtenerCanal() + ": " + e.getMessage());
            }
        }
    }

    @Override
    public String obtenerCanal() {
        return notificadores.stream()
                .map(Notificador::obtenerCanal)
                .collect(Collectors.joining(", "));
    }
}
