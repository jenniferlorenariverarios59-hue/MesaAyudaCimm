package co.edu.sena.mesaayuda.web;

import co.edu.sena.mesaayuda.DTO.UsuarioDTO;
import co.edu.sena.mesaayuda.servicio.AuthService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "AuthServlet", urlPatterns = {"/login", "/logout"})
public class AuthServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getServletPath();
        if ("/logout".equals(path)) {
            SesionUsuario.cerrar(request);
            response.sendRedirect(request.getContextPath() + "/login?msg=sesion_cerrada");
            return;
        }

        // Si ya está autenticado, redirigir a los tickets
        if (SesionUsuario.obtener(request).isPresent()) {
            response.sendRedirect(request.getContextPath() + "/tickets");
            return;
        }

        request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String correo = request.getParameter("correo");
        String password = request.getParameter("password");

        AuthService authService = (AuthService) getServletContext().getAttribute(AppContextListener.AUTH_SERVICE);

        Optional<UsuarioDTO> usuarioOpt = authService.autenticar(correo, password);

        if (usuarioOpt.isPresent()) {
            SesionUsuario.guardar(request, usuarioOpt.get());
            response.sendRedirect(request.getContextPath() + "/tickets");
        } else {
            request.setAttribute("error", "Correo o contraseña incorrectos");
            request.setAttribute("correoIngresado", correo);
            request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
        }
    }
}
