package co.edu.sena.mesaayuda.mapper;

import co.edu.sena.mesaayuda.dto.ComentarioDTO;
import co.edu.sena.mesaayuda.dto.SlaDTO;
import co.edu.sena.mesaayuda.dto.TicketDTO;
import co.edu.sena.mesaayuda.modelo.Ticket;
import co.edu.sena.mesaayuda.modelo.estado.EstadoTicket;
import co.edu.sena.mesaayuda.modelo.estado.TransicionEstadoInvalidaException;
import co.edu.sena.mesaayuda.servicio.sla.SelectorSlaStrategy;
import co.edu.sena.mesaayuda.servicio.sla.SlaStrategy;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TicketMapper {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private static final SelectorSlaStrategy SELECTOR_SLA = new SelectorSlaStrategy();

    public static TicketDTO aDTO(Ticket ticket) {
        if (ticket == null) {
            return null;
        }

        TicketDTO dto = new TicketDTO();
        dto.setId(ticket.getId());
        dto.setTitulo(ticket.getTitulo());
        dto.setDescripcion(ticket.getDescripcion());

        if (ticket.getCategoria() != null) {
            dto.setCategoria(ticket.getCategoria().name());
            dto.setNombreCategoria(ticket.getCategoria().getNombreMostrar());
        }

        if (ticket.getPrioridad() != null) {
            dto.setPrioridad(ticket.getPrioridad().name());
            dto.setNombrePrioridad(ticket.getPrioridad().getNombreMostrar());

            SlaStrategy slaStrategy = SELECTOR_SLA.obtenerEstrategia(ticket.getPrioridad());
            SlaDTO slaInfo = slaStrategy.calcularSlaInfo(ticket.getFechaCreacion());
            dto.setSlaInfo(slaInfo);
        }

        dto.setSolicitante(UsuarioMapper.aDTO(ticket.getSolicitante()));
        dto.setAgente(UsuarioMapper.aDTO(ticket.getAgente()));

        if (ticket.getEstado() != null) {
            dto.setEstado(ticket.getEstado().nombre());
            evaluarBanderasAccion(ticket.getEstado(), dto);
        }

        if (ticket.getFechaCreacion() != null) {
            dto.setFechaCreacionFormateada(ticket.getFechaCreacion().format(FORMATTER));
        }

        if (ticket.getFechaActualizacion() != null) {
            dto.setFechaActualizacionFormateada(ticket.getFechaActualizacion().format(FORMATTER));
        }

        List<ComentarioDTO> comentariosDTO = new ArrayList<>();
        if (ticket.getComentarios() != null) {
            ticket.getComentarios().forEach(c -> comentariosDTO.add(ComentarioMapper.aDTO(c)));
        }
        dto.setComentarios(comentariosDTO);

        return dto;
    }

    /**
     * Evalúa qué acciones son válidas según el patrón State sin cambiar el estado.
     */
    private static void evaluarBanderasAccion(EstadoTicket estado, TicketDTO dto) {
        dto.setPuedeAsignar(esAccionValida(estado::asignar));
        dto.setPuedeIniciar(esAccionValida(estado::iniciar));
        dto.setPuedeResolver(esAccionValida(estado::resolver));
        dto.setPuedeCerrar(esAccionValida(estado::cerrar));
        dto.setPuedeReabrir(esAccionValida(estado::reabrir));
        dto.setPuedeCancelar(esAccionValida(estado::cancelar));
    }

    @FunctionalInterface
    private interface AccionState {
        void ejecutar();
    }

    private static boolean esAccionValida(AccionState accion) {
        try {
            accion.ejecutar();
            return true;
        } catch (TransicionEstadoInvalidaException e) {
            return false;
        }
    }
}
