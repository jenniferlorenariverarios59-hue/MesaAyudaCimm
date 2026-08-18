package co.edu.sena.mesaayuda.servicio;

import co.edu.sena.mesaayuda.DTO.UsuarioDTO;

import java.util.List;
import java.util.Optional;

public interface AuthService {
    Optional<UsuarioDTO> autenticar(String correo, String password);
    Optional<UsuarioDTO> obtenerPorId(Long id);
    List<UsuarioDTO> listarAgentes();
    List<UsuarioDTO> listarTodos();
}
