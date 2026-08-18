package co.edu.sena.mesaayuda.servicio;

import co.edu.sena.mesaayuda.dto.TicketDTO;
import co.edu.sena.mesaayuda.servicio.asignacion.AsignacionStrategy;

import java.util.List;

public interface AdminTicketService {
    List<TicketDTO> listarTodosLosTickets();
    TicketDTO reasignarAgente(Long ticketId, Long nuevoAgenteId);
    TicketDTO cancelarTicket(Long ticketId);
    void cambiarEstrategiaAsignacion(AsignacionStrategy nuevaEstrategia);
    AsignacionStrategy obtenerEstrategiaAsignacionActual();
}
