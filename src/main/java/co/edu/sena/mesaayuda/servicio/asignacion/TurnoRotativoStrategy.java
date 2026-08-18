package co.edu.sena.mesaayuda.servicio.asignacion;

import co.edu.sena.mesaayuda.modelo.Ticket;
import co.edu.sena.mesaayuda.modelo.Usuario;

import java.util.List;

public class TurnoRotativoStrategy implements AsignacionStrategy {

    private int ultimoIndiceAsignado = -1;

    @Override
    public synchronized Usuario seleccionarAgente(List<Usuario> agentesDisponibles, Ticket ticket, List<Ticket> todosLosTickets) {
        if (agentesDisponibles == null || agentesDisponibles.isEmpty()) {
            return null;
        }
        ultimoIndiceAsignado = (ultimoIndiceAsignado + 1) % agentesDisponibles.size();
        return agentesDisponibles.get(ultimoIndiceAsignado);
    }

    @Override
    public String obtenerNombreEstrategia() {
        return "Turno Rotativo (Round Robin)";
    }
}
