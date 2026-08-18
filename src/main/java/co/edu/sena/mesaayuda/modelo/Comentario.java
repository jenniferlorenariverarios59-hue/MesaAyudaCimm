package co.edu.sena.mesaayuda.modelo;

import java.time.LocalDateTime;

public class Comentario {
    private Long id;
    private Long ticketId;
    private Usuario autor;
    private String texto;
    private LocalDateTime fecha;

    public Comentario() {
    }

    public Comentario(Long id, Long ticketId, Usuario autor, String texto, LocalDateTime fecha) {
        this.id = id;
        this.ticketId = ticketId;
        this.autor = autor;
        this.texto = texto;
        this.fecha = fecha;
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

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}
