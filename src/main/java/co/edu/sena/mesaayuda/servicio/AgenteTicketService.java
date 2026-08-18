package co.edu.sena.mesaayuda.servicio;

import co.edu.sena.mesaayuda.DTO.TicketDTO;

import java.util.List;

public interface AgenteTicketService {
    List<TicketDTO> listarTicketsAsignados(Long agenteId);
    TicketDTO iniciarAtencionTicket(Long ticketId, Long agenteId);
    TicketDTO resolverTicket(Long ticketId, Long agenteId);
    TicketDTO agregarComentario(Long ticketId, Long autorId, String texto);
}
