package co.edu.sena.mesaayuda.modelo;

import co.edu.sena.mesaayuda.modelo.estado.EstadoTicket;
import co.edu.sena.mesaayuda.modelo.estado.NuevoState;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Ticket {
    private Long id;
    private String titulo;
    private String descripcion;
    private Categoria categoria;
    private Prioridad prioridad;
    private Usuario solicitante;
    private Usuario agente;
    private EstadoTicket estado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
    private List<Comentario> comentarios = new ArrayList<>();

    public Ticket() {
        this.estado = new NuevoState();
        this.fechaCreacion = LocalDateTime.now();
        this.fechaActualizacion = LocalDateTime.now();
    }

    public Ticket(Long id, String titulo, String descripcion, Categoria categoria, Prioridad prioridad, Usuario solicitante) {
        this();
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.prioridad = prioridad;
        this.solicitante = solicitante;
    }

    // Delegación de transiciones al patrón State
    public void asignarAgente(Usuario agente) {
        this.estado = this.estado.asignar();
        this.agente = agente;
        this.fechaActualizacion = LocalDateTime.now();
    }

    public void iniciarAtencion() {
        this.estado = this.estado.iniciar();
        this.fechaActualizacion = LocalDateTime.now();
    }

    public void resolver() {
        this.estado = this.estado.resolver();
        this.fechaActualizacion = LocalDateTime.now();
    }

    public void cerrar() {
        this.estado = this.estado.cerrar();
        this.fechaActualizacion = LocalDateTime.now();
    }

    public void reabrir() {
        this.estado = this.estado.reabrir();
        this.fechaActualizacion = LocalDateTime.now();
    }

    public void cancelar() {
        this.estado = this.estado.cancelar();
        this.fechaActualizacion = LocalDateTime.now();
    }

    public void agregarComentario(Comentario comentario) {
        this.comentarios.add(comentario);
        this.fechaActualizacion = LocalDateTime.now();
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Prioridad getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Prioridad prioridad) {
        this.prioridad = prioridad;
    }

    public Usuario getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(Usuario solicitante) {
        this.solicitante = solicitante;
    }

    public Usuario getAgente() {
        return agente;
    }

    public void setAgente(Usuario agente) {
        this.agente = agente;
    }

    public EstadoTicket getEstado() {
        return estado;
    }

    public void setEstado(EstadoTicket estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(id, ticket.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
