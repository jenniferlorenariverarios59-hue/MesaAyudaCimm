package co.edu.sena.mesaayuda.servicio;

import co.edu.sena.mesaayuda.dto.TicketDTO;
import co.edu.sena.mesaayuda.mapper.TicketMapper;
import co.edu.sena.mesaayuda.modelo.Categoria;
import co.edu.sena.mesaayuda.modelo.Comentario;
import co.edu.sena.mesaayuda.modelo.Prioridad;
import co.edu.sena.mesaayuda.modelo.Rol;
import co.edu.sena.mesaayuda.modelo.Ticket;
import co.edu.sena.mesaayuda.modelo.Usuario;
import co.edu.sena.mesaayuda.repositorio.TicketRepository;
import co.edu.sena.mesaayuda.repositorio.UsuarioRepository;
import co.edu.sena.mesaayuda.servicio.asignacion.AsignacionStrategy;
import co.edu.sena.mesaayuda.servicio.notificacion.Notificador;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final UsuarioRepository usuarioRepository;
    private AsignacionStrategy asignacionStrategy;
    private final Notificador notificador;
    private final AtomicLong secuenciaComentarios = new AtomicLong(0);

    public TicketServiceImpl(TicketRepository ticketRepository,
                             UsuarioRepository usuarioRepository,
                             AsignacionStrategy asignacionStrategy,
                             Notificador notificador) {
        this.ticketRepository = ticketRepository;
        this.usuarioRepository = usuarioRepository;
        this.asignacionStrategy = asignacionStrategy;
        this.notificador = notificador;
    }

    @Override
    public TicketDTO crearTicket(String titulo, String descripcion, String categoriaStr, Long solicitanteId) {
        Usuario solicitante = usuarioRepository.buscarPorId(solicitanteId)
                .orElseThrow(() -> new IllegalArgumentException("Solicitante no encontrado id: " + solicitanteId));

        Categoria categoria = parsearCategoria(categoriaStr);
        Prioridad prioridad = determinarPrioridadAutomatica(titulo, descripcion, categoria);

        Ticket ticket = new Ticket();
        ticket.setTitulo(titulo);
        ticket.setDescripcion(descripcion);
        ticket.setCategoria(categoria);
        ticket.setPrioridad(prioridad);
        ticket.setSolicitante(solicitante);

        // Guardado inicial para asignar ID
        ticketRepository.guardar(ticket);

        // Asignación automática de agente usando la Strategy inyectada (RF-04)
        List<Usuario> agentes = usuarioRepository.buscarPorRol(Rol.AGENTE);
        List<Ticket> todosLosTickets = ticketRepository.listarTodos();
        Usuario agenteAsignado = asignacionStrategy.seleccionarAgente(agentes, ticket, todosLosTickets);

        if (agenteAsignado != null) {
            // El patrón State transiciona de NUEVO a ASIGNADO
            ticket.asignarAgente(agenteAsignado);
            ticketRepository.guardar(ticket);
        }

        // Notificación de creación/asignación (RF-08)
        notificador.notificarCambioEstado(ticket, "NUEVO", ticket.getEstado().nombre());

        return TicketMapper.aDTO(ticket);
    }

    @Override
    public List<TicketDTO> listarMisTickets(Long solicitanteId) {
        return ticketRepository.buscarPorSolicitante(solicitanteId).stream()
                .map(TicketMapper::aDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TicketDTO> listarTicketsAsignados(Long agenteId) {
        return ticketRepository.buscarPorAgente(agenteId).stream()
                .map(TicketMapper::aDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TicketDTO> listarTodosLosTickets() {
        return ticketRepository.listarTodos().stream()
                .map(TicketMapper::aDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TicketDTO obtenerTicketPorId(Long id) {
        return ticketRepository.buscarPorId(id)
                .map(TicketMapper::aDTO)
                .orElseThrow(() -> new IllegalArgumentException("Ticket no encontrado id: " + id));
    }

    @Override
    public TicketDTO iniciarAtencionTicket(Long ticketId, Long agenteId) {
        Ticket ticket = obtenerEntidadTicket(ticketId);
        String estadoAnterior = ticket.getEstado().nombre();

        // Aplicar cambio de estado mediante el patrón State (ASIGNADO -> EN_PROCESO)
        ticket.iniciarAtencion();
        ticketRepository.guardar(ticket);

        notificador.notificarCambioEstado(ticket, estadoAnterior, ticket.getEstado().nombre());
        return TicketMapper.aDTO(ticket);
    }

    @Override
    public TicketDTO resolverTicket(Long ticketId, Long agenteId) {
        Ticket ticket = obtenerEntidadTicket(ticketId);
        String estadoAnterior = ticket.getEstado().nombre();

        // Aplicar cambio de estado mediante el patrón State (EN_PROCESO -> RESUELTO)
        ticket.resolver();
        ticketRepository.guardar(ticket);

        notificador.notificarCambioEstado(ticket, estadoAnterior, ticket.getEstado().nombre());
        return TicketMapper.aDTO(ticket);
    }

    @Override
    public TicketDTO confirmarCierreTicket(Long ticketId, Long usuarioId) {
        Ticket ticket = obtenerEntidadTicket(ticketId);
        String estadoAnterior = ticket.getEstado().nombre();

        // Aplicar cambio de estado mediante el patrón State (RESUELTO -> CERRADO)
        ticket.cerrar();
        ticketRepository.guardar(ticket);

        notificador.notificarCambioEstado(ticket, estadoAnterior, ticket.getEstado().nombre());
        return TicketMapper.aDTO(ticket);
    }

    @Override
    public TicketDTO reabrirTicket(Long ticketId, Long usuarioId) {
        Ticket ticket = obtenerEntidadTicket(ticketId);
        String estadoAnterior = ticket.getEstado().nombre();

        // Aplicar cambio de estado mediante el patrón State (RESUELTO -> EN_PROCESO)
        ticket.reabrir();
        ticketRepository.guardar(ticket);

        notificador.notificarCambioEstado(ticket, estadoAnterior, ticket.getEstado().nombre());
        return TicketMapper.aDTO(ticket);
    }

    @Override
    public TicketDTO cancelarTicket(Long ticketId) {
        Ticket ticket = obtenerEntidadTicket(ticketId);
        String estadoAnterior = ticket.getEstado().nombre();

        // Aplicar cambio de estado mediante el patrón State (Cualquiera excepto CERRADO -> CANCELADO)
        ticket.cancelar();
        ticketRepository.guardar(ticket);

        notificador.notificarCambioEstado(ticket, estadoAnterior, ticket.getEstado().nombre());
        return TicketMapper.aDTO(ticket);
    }

    @Override
    public TicketDTO reasignarAgente(Long ticketId, Long nuevoAgenteId) {
        Ticket ticket = obtenerEntidadTicket(ticketId);
        Usuario nuevoAgente = usuarioRepository.buscarPorId(nuevoAgenteId)
                .orElseThrow(() -> new IllegalArgumentException("Agente no encontrado id: " + nuevoAgenteId));

        String estadoAnterior = ticket.getEstado().nombre();
        if ("NUEVO".equalsIgnoreCase(estadoAnterior)) {
            ticket.asignarAgente(nuevoAgente);
        } else {
            ticket.setAgente(nuevoAgente);
            ticket.setFechaActualizacion(LocalDateTime.now());
        }

        ticketRepository.guardar(ticket);
        notificador.notificarCambioEstado(ticket, estadoAnterior, ticket.getEstado().nombre() + " (Reasignado)");
        return TicketMapper.aDTO(ticket);
    }

    @Override
    public TicketDTO agregarComentario(Long ticketId, Long autorId, String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new IllegalArgumentException("El comentario no puede estar vacío");
        }

        Ticket ticket = obtenerEntidadTicket(ticketId);
        Usuario autor = usuarioRepository.buscarPorId(autorId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario autor no encontrado id: " + autorId));

        Comentario comentario = new Comentario(
                secuenciaComentarios.incrementAndGet(),
                ticketId,
                autor,
                texto.trim(),
                LocalDateTime.now()
        );

        ticket.agregarComentario(comentario);
        ticketRepository.guardar(ticket);

        return TicketMapper.aDTO(ticket);
    }

    @Override
    public void cambiarEstrategiaAsignacion(AsignacionStrategy nuevaEstrategia) {
        if (nuevaEstrategia != null) {
            this.asignacionStrategy = nuevaEstrategia;
        }
    }

    @Override
    public AsignacionStrategy obtenerEstrategiaAsignacionActual() {
        return this.asignacionStrategy;
    }

    private Ticket obtenerEntidadTicket(Long ticketId) {
        return ticketRepository.buscarPorId(ticketId)
                .orElseThrow(() -> new IllegalArgumentException("Ticket no encontrado id: " + ticketId));
    }

    private Categoria parsearCategoria(String categoriaStr) {
        if (categoriaStr == null || categoriaStr.trim().isEmpty()) {
            return Categoria.OTRO;
        }
        try {
            return Categoria.valueOf(categoriaStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            return Categoria.OTRO;
        }
    }

    private Prioridad determinarPrioridadAutomatica(String titulo, String descripcion, Categoria categoria) {
        String contenido = ((titulo != null ? titulo : "") + " " + (descripcion != null ? descripcion : "")).toLowerCase();

        if (contenido.contains("caido") || contenido.contains("caído") || contenido.contains("servidor") ||
            contenido.contains("urgente") || contenido.contains("critico") || contenido.contains("crítico") ||
            contenido.contains("bloqueo")) {
            return Prioridad.CRITICA;
        }

        if (contenido.contains("error") || contenido.contains("falla") || contenido.contains("lento") ||
            contenido.contains("no funciona") || contenido.contains("sin internet")) {
            return Prioridad.ALTA;
        }

        if (categoria == Categoria.RED || categoria == Categoria.SOFTWARE) {
            return Prioridad.MEDIA;
        }

        return Prioridad.BAJA;
    }
}
