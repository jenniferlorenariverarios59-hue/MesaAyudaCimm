package co.edu.sena.mesaayuda.servicio;

import co.edu.sena.mesaayuda.DTO.TicketDTO;

import java.util.List;

public interface SolicitanteTicketService {
    TicketDTO crearTicket(String titulo, String descripcion, String categoriaStr, Long solicitanteId);
    List<TicketDTO> listarMisTickets(Long solicitanteId);
    TicketDTO obtenerTicketPorId(Long id);
    TicketDTO confirmarCierreTicket(Long ticketId, Long usuarioId);
    TicketDTO reabrirTicket(Long ticketId, Long usuarioId);
    TicketDTO agregarComentario(Long ticketId, Long autorId, String texto);
}
