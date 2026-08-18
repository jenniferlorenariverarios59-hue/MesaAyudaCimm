package co.edu.sena.mesaayuda.repositorio;

import co.edu.sena.mesaayuda.modelo.Categoria;
import co.edu.sena.mesaayuda.modelo.Ticket;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class TicketRepositoryEnMemoria implements TicketRepository {

    private final Map<Long, Ticket> tickets = new ConcurrentHashMap<>();
    private final AtomicLong secuenciaId = new AtomicLong(0);

    @Override
    public Ticket guardar(Ticket ticket) {
        if (ticket.getId() == null) {
            ticket.setId(secuenciaId.incrementAndGet());
        }
        tickets.put(ticket.getId(), ticket);
        return ticket;
    }

    @Override
    public Optional<Ticket> buscarPorId(Long id) {
        return Optional.ofNullable(tickets.get(id));
    }

    @Override
    public List<Ticket> buscarPorSolicitante(Long solicitanteId) {
        if (solicitanteId == null) {
            return new ArrayList<>();
        }
        return tickets.values().stream()
                .filter(t -> t.getSolicitante() != null && solicitanteId.equals(t.getSolicitante().getId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Ticket> buscarPorAgente(Long agenteId) {
        if (agenteId == null) {
            return new ArrayList<>();
        }
        return tickets.values().stream()
                .filter(t -> t.getAgente() != null && agenteId.equals(t.getAgente().getId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Ticket> buscarPorCategoria(Categoria categoria) {
        if (categoria == null) {
            return new ArrayList<>();
        }
        return tickets.values().stream()
                .filter(t -> t.getCategoria() == categoria)
                .collect(Collectors.toList());
    }

    @Override
    public List<Ticket> listarTodos() {
        return new ArrayList<>(tickets.values());
    }
}
