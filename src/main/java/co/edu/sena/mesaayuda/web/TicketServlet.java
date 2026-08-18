package co.edu.sena.mesaayuda.web;

import co.edu.sena.mesaayuda.DTO.TicketDTO;
import co.edu.sena.mesaayuda.DTO.UsuarioDTO;
import co.edu.sena.mesaayuda.modelo.Categoria;
import co.edu.sena.mesaayuda.modelo.estado.TransicionEstadoInvalidaException;
import co.edu.sena.mesaayuda.servicio.TicketService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@WebServlet(name = "TicketServlet", urlPatterns = {"/tickets", "/tickets/crear", "/tickets/detalle", "/tickets/accion"})
public class TicketServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Optional<UsuarioDTO> usuarioOpt = SesionUsuario.obtener(request);
        if (usuarioOpt.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        UsuarioDTO usuario = usuarioOpt.get();
        TicketService ticketService = (TicketService) getServletContext().getAttribute(AppContextListener.TICKET_SERVICE);

        String path = request.getServletPath();

        switch (path) {
            case "/tickets/crear":
                request.setAttribute("categorias", Categoria.values());
                request.getRequestDispatcher("/WEB-INF/jsp/crear-ticket.jsp").forward(request, response);
                break;

            case "/tickets/detalle":
                String idStr = request.getParameter("id");
                if (idStr != null) {
                    try {
                        Long id = Long.parseLong(idStr);
                        TicketDTO ticket = ticketService.obtenerTicketPorId(id);
                        request.setAttribute("ticket", ticket);
                        request.setAttribute("usuarioLogueado", usuario);
                        request.getRequestDispatcher("/WEB-INF/jsp/detalle-ticket.jsp").forward(request, response);
                        return;
                    } catch (Exception e) {
                        request.setAttribute("error", "Error cargando ticket: " + e.getMessage());
                    }
                }
                response.sendRedirect(request.getContextPath() + "/tickets");
                break;

            case "/tickets":
            default:
                List<TicketDTO> tickets;
                if (SesionUsuario.esSolicitante(usuario)) {
                    tickets = ticketService.listarMisTickets(usuario.getId());
                } else if (SesionUsuario.esAgente(usuario)) {
                    tickets = ticketService.listarTicketsAsignados(usuario.getId());
                } else { // ADMINISTRADOR
                    tickets = ticketService.listarTodosLosTickets();
                }

                // Filtrado opcional por estado o categoría
                String filtroEstado = request.getParameter("estado");
                if (filtroEstado != null && !filtroEstado.trim().isEmpty()) {
                    tickets = tickets.stream()
                            .filter(t -> t.getEstado().equalsIgnoreCase(filtroEstado))
                            .collect(Collectors.toList());
                }

                String filtroCategoria = request.getParameter("categoria");
                if (filtroCategoria != null && !filtroCategoria.trim().isEmpty()) {
                    tickets = tickets.stream()
                            .filter(t -> t.getCategoria().equalsIgnoreCase(filtroCategoria))
                            .collect(Collectors.toList());
                }

                request.setAttribute("tickets", tickets);
                request.setAttribute("usuarioLogueado", usuario);
                request.setAttribute("categorias", Categoria.values());
                request.setAttribute("filtroEstado", filtroEstado);
                request.setAttribute("filtroCategoria", filtroCategoria);
                request.getRequestDispatcher("/WEB-INF/jsp/tickets.jsp").forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Optional<UsuarioDTO> usuarioOpt = SesionUsuario.obtener(request);
        if (usuarioOpt.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        UsuarioDTO usuario = usuarioOpt.get();
        TicketService ticketService = (TicketService) getServletContext().getAttribute(AppContextListener.TICKET_SERVICE);

        String path = request.getServletPath();

        if ("/tickets/crear".equals(path)) {
            String titulo = request.getParameter("titulo");
            String descripcion = request.getParameter("descripcion");
            String categoria = request.getParameter("categoria");

            try {
                TicketDTO nuevoTicket = ticketService.crearTicket(titulo, descripcion, categoria, usuario.getId());
                response.sendRedirect(request.getContextPath() + "/tickets/detalle?id=" + nuevoTicket.getId() + "&msg=creado");
            } catch (Exception e) {
                request.setAttribute("error", "Error creando el ticket: " + e.getMessage());
                request.setAttribute("categorias", Categoria.values());
                request.getRequestDispatcher("/WEB-INF/jsp/crear-ticket.jsp").forward(request, response);
            }
            return;
        }

        if ("/tickets/accion".equals(path)) {
            String accion = request.getParameter("accion");
            String idStr = request.getParameter("ticketId");

            if (idStr == null || accion == null) {
                response.sendRedirect(request.getContextPath() + "/tickets");
                return;
            }

            Long ticketId = Long.parseLong(idStr);

            try {
                switch (accion) {
                    case "iniciar":
                        ticketService.iniciarAtencionTicket(ticketId, usuario.getId());
                        break;
                    case "resolver":
                        ticketService.resolverTicket(ticketId, usuario.getId());
                        break;
                    case "cerrar":
                        ticketService.confirmarCierreTicket(ticketId, usuario.getId());
                        break;
                    case "reabrir":
                        ticketService.reabrirTicket(ticketId, usuario.getId());
                        break;
                    case "cancelar":
                        ticketService.cancelarTicket(ticketId);
                        break;
                    case "comentar":
                        String textoComentario = request.getParameter("textoComentario");
                        ticketService.agregarComentario(ticketId, usuario.getId(), textoComentario);
                        break;
                }
                response.sendRedirect(request.getContextPath() + "/tickets/detalle?id=" + ticketId + "&msg=ok");
            } catch (TransicionEstadoInvalidaException e) {
                response.sendRedirect(request.getContextPath() + "/tickets/detalle?id=" + ticketId + "&error=" + e.getMessage());
            } catch (Exception e) {
                response.sendRedirect(request.getContextPath() + "/tickets/detalle?id=" + ticketId + "&error=" + e.getMessage());
            }
        }
    }
}
