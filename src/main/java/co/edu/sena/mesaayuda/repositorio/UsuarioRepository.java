package co.edu.sena.mesaayuda.repositorio;

import co.edu.sena.mesaayuda.modelo.Rol;
import co.edu.sena.mesaayuda.modelo.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository {
    Usuario guardar(Usuario usuario);
    Optional<Usuario> buscarPorId(Long id);
    Optional<Usuario> buscarPorCorreo(String correo);
    List<Usuario> buscarPorRol(Rol rol);
    List<Usuario> listarTodos();
}
