package co.edu.sena.mesaayuda.mapper;

import co.edu.sena.mesaayuda.DTO.ComentarioDTO;
import co.edu.sena.mesaayuda.modelo.Comentario;

import java.time.format.DateTimeFormatter;

public class ComentarioMapper {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public static ComentarioDTO aDTO(Comentario comentario) {
        if (comentario == null) {
            return null;
        }
        String fechaFormateada = comentario.getFecha() != null ? comentario.getFecha().format(FORMATTER) : "";
        return new ComentarioDTO(
                comentario.getId(),
                comentario.getTicketId(),
                UsuarioMapper.aDTO(comentario.getAutor()),
                comentario.getTexto(),
                fechaFormateada
        );
    }
}
