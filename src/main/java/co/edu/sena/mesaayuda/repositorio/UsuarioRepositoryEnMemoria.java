package co.edu.sena.mesaayuda.repositorio;

import co.edu.sena.mesaayuda.modelo.Rol;
import co.edu.sena.mesaayuda.modelo.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class UsuarioRepositoryEnMemoria implements UsuarioRepository {

    private final Map<Long, Usuario> usuarios = new ConcurrentHashMap<>();
    private final AtomicLong secuenciaId = new AtomicLong(0);

    @Override
    public Usuario guardar(Usuario usuario) {
        if (usuario.getId() == null) {
            usuario.setId(secuenciaId.incrementAndGet());
        }
        usuarios.put(usuario.getId(), usuario);
        return usuario;
    }

    @Override
    public Optional<Usuario> buscarPorId(Long id) {
        return Optional.ofNullable(usuarios.get(id));
    }

    @Override
    public Optional<Usuario> buscarPorCorreo(String correo) {
        if (correo == null) {
            return Optional.empty();
        }
        return usuarios.values().stream()
                .filter(u -> correo.equalsIgnoreCase(u.getCorreo()))
                .findFirst();
    }

    @Override
    public List<Usuario> buscarPorRol(Rol rol) {
        if (rol == null) {
            return new ArrayList<>();
        }
        return usuarios.values().stream()
                .filter(u -> u.getRol() == rol)
                .collect(Collectors.toList());
    }

    @Override
    public List<Usuario> listarTodos() {
        return new ArrayList<>(usuarios.values());
    }
}
