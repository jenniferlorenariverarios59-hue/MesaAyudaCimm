package co.edu.sena.mesaayuda.dto;

public class ComentarioDTO {
    private Long id;
    private Long ticketId;
    private UsuarioDTO autor;
    private String texto;
    private String fechaFormateada;

    public ComentarioDTO() {
    }

    public ComentarioDTO(Long id, Long ticketId, UsuarioDTO autor, String texto, String fechaFormateada) {
        this.id = id;
        this.ticketId = ticketId;
        this.autor = autor;
        this.texto = texto;
        this.fechaFormateada = fechaFormateada;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public UsuarioDTO getAutor() {
        return autor;
    }

    public void setAutor(UsuarioDTO autor) {
        this.autor = autor;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getFechaFormateada() {
        return fechaFormateada;
    }

    public void setFechaFormateada(String fechaFormateada) {
        this.fechaFormateada = fechaFormateada;
    }
}
