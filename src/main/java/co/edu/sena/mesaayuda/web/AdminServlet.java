package co.edu.sena.mesaayuda.web;

import co.edu.sena.mesaayuda.dto.TicketDTO;
import co.edu.sena.mesaayuda.dto.UsuarioDTO;
import co.edu.sena.mesaayuda.servicio.AuthService;
import co.edu.sena.mesaayuda.servicio.TicketService;
import co.edu.sena.mesaayuda.servicio.asignacion.AsignacionStrategy;
import co.edu.sena.mesaayuda.servicio.asignacion.CargaMinimaStrategy;
import co.edu.sena.mesaayuda.servicio.asignacion.PorCategoriaStrategy;
import co.edu.sena.mesaayuda.servicio.asignacion.TurnoRotativoStrategy;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "AdminServlet", urlPatterns = {"/admin", "/admin/reasignar", "/admin/estrategia"})
public class AdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Optional<UsuarioDTO> usuarioOpt = SesionUsuario.obtener(request);
        if (usuarioOpt.isEmpty() || !SesionUsuario.esAdmin(usuarioOpt.get())) {
            response.sendRedirect(request.getContextPath() + "/tickets");
            return;
        }

        TicketService ticketService = (TicketService) getServletContext().getAttribute(AppContextListener.TICKET_SERVICE);
        AuthService authService = (AuthService) getServletContext().getAttribute(AppContextListener.AUTH_SERVICE);

        List<TicketDTO> tickets = ticketService.listarTodosLosTickets();
        List<UsuarioDTO> agentes = authService.listarAgentes();
        AsignacionStrategy estrategiaActual = ticketService.obtenerEstrategiaAsignacionActual();

        request.setAttribute("tickets", tickets);
        request.setAttribute("agentes", agentes);
        request.setAttribute("estrategiaActual", estrategiaActual.obtenerNombreEstrategia());
        request.getRequestDispatcher("/WEB-INF/jsp/admin.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Optional<UsuarioDTO> usuarioOpt = SesionUsuario.obtener(request);
        if (usuarioOpt.isEmpty() || !SesionUsuario.esAdmin(usuarioOpt.get())) {
            response.sendRedirect(request.getContextPath() + "/tickets");
            return;
        }

        TicketService ticketService = (TicketService) getServletContext().getAttribute(AppContextListener.TICKET_SERVICE);

        String path = request.getServletPath();

        if ("/admin/reasignar".equals(path)) {
            String ticketIdStr = request.getParameter("ticketId");
            String agenteIdStr = request.getParameter("agenteId");

            if (ticketIdStr != null && agenteIdStr != null) {
                try {
                    Long ticketId = Long.parseLong(ticketIdStr);
                    Long agenteId = Long.parseLong(agenteIdStr);
                    ticketService.reasignarAgente(ticketId, agenteId);
                } catch (Exception e) {
                    System.err.println("Error reasignando agente: " + e.getMessage());
                }
            }
            response.sendRedirect(request.getContextPath() + "/admin?msg=reasignado");
            return;
        }

        if ("/admin/estrategia".equals(path)) {
            String estrategiaStr = request.getParameter("tipoEstrategia");
            if (estrategiaStr != null) {
                switch (estrategiaStr) {
                    case "carga":
                        ticketService.cambiarEstrategiaAsignacion(new CargaMinimaStrategy());
                        break;
                    case "categoria":
                        ticketService.cambiarEstrategiaAsignacion(new PorCategoriaStrategy());
                        break;
                    case "rotativo":
                    default:
                        ticketService.cambiarEstrategiaAsignacion(new TurnoRotativoStrategy());
                        break;
                }
            }
            response.sendRedirect(request.getContextPath() + "/admin?msg=estrategia_cambiada");
        }
    }
}
