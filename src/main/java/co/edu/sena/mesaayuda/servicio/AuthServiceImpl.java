package co.edu.sena.mesaayuda.servicio;

import co.edu.sena.mesaayuda.dto.UsuarioDTO;
import co.edu.sena.mesaayuda.mapper.UsuarioMapper;
import co.edu.sena.mesaayuda.modelo.Rol;
import co.edu.sena.mesaayuda.modelo.Usuario;
import co.edu.sena.mesaayuda.repositorio.UsuarioRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AuthServiceImpl implements AuthService {

    private final UsuarioRepository usuarioRepository;

    public AuthServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Optional<UsuarioDTO> autenticar(String correo, String password) {
        if (correo == null || password == null) {
            return Optional.empty();
        }
        Optional<Usuario> usuarioOpt = usuarioRepository.buscarPorCorreo(correo);
        if (usuarioOpt.isPresent() && password.equals(usuarioOpt.get().getPassword())) {
            return Optional.of(UsuarioMapper.aDTO(usuarioOpt.get()));
        }
        return Optional.empty();
    }

    @Override
    public Optional<UsuarioDTO> obtenerPorId(Long id) {
        return usuarioRepository.buscarPorId(id).map(UsuarioMapper::aDTO);
    }

    @Override
    public List<UsuarioDTO> listarAgentes() {
        return usuarioRepository.buscarPorRol(Rol.AGENTE).stream()
                .map(UsuarioMapper::aDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UsuarioDTO> listarTodos() {
        return usuarioRepository.listarTodos().stream()
                .map(UsuarioMapper::aDTO)
                .collect(Collectors.toList());
    }
}
