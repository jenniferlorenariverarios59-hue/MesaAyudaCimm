package co.edu.sena.mesaayuda.servicio.asignacion;

import co.edu.sena.mesaayuda.modelo.Ticket;
import co.edu.sena.mesaayuda.modelo.Usuario;

import java.util.List;

public interface AsignacionStrategy {
    Usuario seleccionarAgente(List<Usuario> agentesDisponibles, Ticket ticket, List<Ticket> todosLosTickets);
    String obtenerNombreEstrategia();
}
