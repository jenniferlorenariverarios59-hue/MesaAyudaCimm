package co.edu.sena.mesaayuda.mapper;

import co.edu.sena.mesaayuda.DTO.UsuarioDTO;
import co.edu.sena.mesaayuda.modelo.Usuario;

public class UsuarioMapper {

    public static UsuarioDTO aDTO(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        return new UsuarioDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getCorreo(),
                usuario.getRol().name(),
                usuario.getRol().getNombreMostrar(),
                usuario.getEspecialidad()
        );
    }
}
