package co.edu.sena.mesaayuda.web;

import co.edu.sena.mesaayuda.dto.UsuarioDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class SesionUsuario {

    public static final String ATRIBUTO_USUARIO = "usuarioLogueado";

    public static void guardar(HttpServletRequest request, UsuarioDTO usuario) {
        HttpSession session = request.getSession(true);
        session.setAttribute(ATRIBUTO_USUARIO, usuario);
    }

    public static Optional<UsuarioDTO> obtener(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return Optional.empty();
        }
        Object obj = session.getAttribute(ATRIBUTO_USUARIO);
        if (obj instanceof UsuarioDTO) {
            return Optional.of((UsuarioDTO) obj);
        }
        return Optional.empty();
    }

    public static void cerrar(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }

    public static boolean esSolicitante(UsuarioDTO usuario) {
        return usuario != null && "SOLICITANTE".equalsIgnoreCase(usuario.getRol());
    }

    public static boolean esAgente(UsuarioDTO usuario) {
        return usuario != null && "AGENTE".equalsIgnoreCase(usuario.getRol());
    }

    public static boolean esAdmin(UsuarioDTO usuario) {
        return usuario != null && "ADMINISTRADOR".equalsIgnoreCase(usuario.getRol());
    }
}
