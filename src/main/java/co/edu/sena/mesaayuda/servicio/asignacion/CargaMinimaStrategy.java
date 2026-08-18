package co.edu.sena.mesaayuda.servicio.asignacion;

import co.edu.sena.mesaayuda.modelo.Ticket;
import co.edu.sena.mesaayuda.modelo.Usuario;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class CargaMinimaStrategy implements AsignacionStrategy {

    @Override
    public Usuario seleccionarAgente(List<Usuario> agentesDisponibles, Ticket ticket, List<Ticket> todosLosTickets) {
        if (agentesDisponibles == null || agentesDisponibles.isEmpty()) {
            return null;
        }

        Map<Usuario, Long> conteoTicketsActivos = new HashMap<>();
        for (Usuario agente : agentesDisponibles) {
            conteoTicketsActivos.put(agente, 0L);
        }

        if (todosLosTickets != null) {
            for (Ticket t : todosLosTickets) {
                if (t.getAgente() != null && conteoTicketsActivos.containsKey(t.getAgente())) {
                    String estado = t.getEstado() != null ? t.getEstado().nombre() : "";
                    if (!"CERRADO".equalsIgnoreCase(estado) && !"CANCELADO".equalsIgnoreCase(estado) && !"RESUELTO".equalsIgnoreCase(estado)) {
                        conteoTicketsActivos.put(t.getAgente(), conteoTicketsActivos.get(t.getAgente()) + 1);
                    }
                }
            }
        }

        return agentesDisponibles.stream()
                .min(Comparator.comparingLong(conteoTicketsActivos::get))
                .orElse(agentesDisponibles.get(0));
    }

    @Override
    public String obtenerNombreEstrategia() {
        return "Menor Carga de Trabajo";
    }
}
