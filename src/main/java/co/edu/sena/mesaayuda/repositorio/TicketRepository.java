package co.edu.sena.mesaayuda.repositorio;

import co.edu.sena.mesaayuda.modelo.Categoria;
import co.edu.sena.mesaayuda.modelo.Ticket;

import java.util.List;
import java.util.Optional;

public interface TicketRepository {
    Ticket guardar(Ticket ticket);
    Optional<Ticket> buscarPorId(Long id);
    List<Ticket> buscarPorSolicitante(Long solicitanteId);
    List<Ticket> buscarPorAgente(Long agenteId);
    List<Ticket> buscarPorCategoria(Categoria categoria);
    List<Ticket> listarTodos();
}
