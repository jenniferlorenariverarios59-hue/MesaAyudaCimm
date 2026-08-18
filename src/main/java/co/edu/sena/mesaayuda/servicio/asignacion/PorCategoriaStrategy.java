package co.edu.sena.mesaayuda.servicio.asignacion;

import co.edu.sena.mesaayuda.modelo.Ticket;
import co.edu.sena.mesaayuda.modelo.Usuario;

import java.util.List;
import java.util.stream.Collectors;

public class PorCategoriaStrategy implements AsignacionStrategy {

    private final TurnoRotativoStrategy fallbackStrategy = new TurnoRotativoStrategy();

    @Override
    public Usuario seleccionarAgente(List<Usuario> agentesDisponibles, Ticket ticket, List<Ticket> todosLosTickets) {
        if (agentesDisponibles == null || agentesDisponibles.isEmpty()) {
            return null;
        }

        if (ticket != null && ticket.getCategoria() != null) {
            String categoriaBuscada = ticket.getCategoria().name();
            List<Usuario> agentesEspecializados = agentesDisponibles.stream()
                    .filter(a -> a.getEspecialidad() != null && a.getEspecialidad().equalsIgnoreCase(categoriaBuscada))
                    .collect(Collectors.toList());

            if (!agentesEspecializados.isEmpty()) {
                return fallbackStrategy.seleccionarAgente(agentesEspecializados, ticket, todosLosTickets);
            }
        }

        return fallbackStrategy.seleccionarAgente(agentesDisponibles, ticket, todosLosTickets);
    }

    @Override
    public String obtenerNombreEstrategia() {
        return "Especialidad por Categoría";
    }
}
