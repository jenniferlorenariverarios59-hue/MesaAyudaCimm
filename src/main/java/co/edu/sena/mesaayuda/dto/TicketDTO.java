package co.edu.sena.mesaayuda.dto;

import java.util.ArrayList;
import java.util.List;

public class TicketDTO {
    private Long id;
    private String titulo;
    private String descripcion;
    private String categoria;
    private String nombreCategoria;
    private String prioridad;
    private String nombrePrioridad;
    private UsuarioDTO solicitante;
    private UsuarioDTO agente;
    private String estado;
    private String fechaCreacionFormateada;
    private String fechaActualizacionFormateada;
    private SlaDTO slaInfo;
    private List<ComentarioDTO> comentarios = new ArrayList<>();

    // Banderas de acciones permitidas según el Estado actual (Patrón State)
    private boolean puedeAsignar;
    private boolean puedeIniciar;
    private boolean puedeResolver;
    private boolean puedeCerrar;
    private boolean puedeReabrir;
    private boolean puedeCancelar;

    public TicketDTO() {
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

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public String getNombrePrioridad() {
        return nombrePrioridad;
    }

    public void setNombrePrioridad(String nombrePrioridad) {
        this.nombrePrioridad = nombrePrioridad;
    }

    public UsuarioDTO getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(UsuarioDTO solicitante) {
        this.solicitante = solicitante;
    }

    public UsuarioDTO getAgente() {
        return agente;
    }

    public void setAgente(UsuarioDTO agente) {
        this.agente = agente;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFechaCreacionFormateada() {
        return fechaCreacionFormateada;
    }

    public void setFechaCreacionFormateada(String fechaCreacionFormateada) {
        this.fechaCreacionFormateada = fechaCreacionFormateada;
    }

    public String getFechaActualizacionFormateada() {
        return fechaActualizacionFormateada;
    }

    public void setFechaActualizacionFormateada(String fechaActualizacionFormateada) {
        this.fechaActualizacionFormateada = fechaActualizacionFormateada;
    }

    public SlaDTO getSlaInfo() {
        return slaInfo;
    }

    public void setSlaInfo(SlaDTO slaInfo) {
        this.slaInfo = slaInfo;
    }

    public List<ComentarioDTO> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<ComentarioDTO> comentarios) {
        this.comentarios = comentarios;
    }

    public boolean isPuedeAsignar() {
        return puedeAsignar;
    }

    public void setPuedeAsignar(boolean puedeAsignar) {
        this.puedeAsignar = puedeAsignar;
    }

    public boolean isPuedeIniciar() {
        return puedeIniciar;
    }

    public void setPuedeIniciar(boolean puedeIniciar) {
        this.puedeIniciar = puedeIniciar;
    }

    public boolean isPuedeResolver() {
        return puedeResolver;
    }

    public void setPuedeResolver(boolean puedeResolver) {
        this.puedeResolver = puedeResolver;
    }

    public boolean isPuedeCerrar() {
        return puedeCerrar;
    }

    public void setPuedeCerrar(boolean puedeCerrar) {
        this.puedeCerrar = puedeCerrar;
    }

    public boolean isPuedeReabrir() {
        return puedeReabrir;
    }

    public void setPuedeReabrir(boolean puedeReabrir) {
        this.puedeReabrir = puedeReabrir;
    }

    public boolean isPuedeCancelar() {
        return puedeCancelar;
    }

    public void setPuedeCancelar(boolean puedeCancelar) {
        this.puedeCancelar = puedeCancelar;
    }
}
