package co.edu.sena.mesaayuda.DTO;

public class SlaDTO {
    private int horasMaximas;
    private String fechaLimiteFormateada;
    private long horasRestantes;
    private String estadoSla; // "A TIEMPO", "POR VENCER", "VENCIDO"
    private String descripcion;

    public SlaDTO() {
    }

    public SlaDTO(int horasMaximas, String fechaLimiteFormateada, long horasRestantes, String estadoSla, String descripcion) {
        this.horasMaximas = horasMaximas;
        this.fechaLimiteFormateada = fechaLimiteFormateada;
        this.horasRestantes = horasRestantes;
        this.estadoSla = estadoSla;
        this.descripcion = descripcion;
    }

    public int getHorasMaximas() {
        return horasMaximas;
    }

    public void setHorasMaximas(int horasMaximas) {
        this.horasMaximas = horasMaximas;
    }

    public String getFechaLimiteFormateada() {
        return fechaLimiteFormateada;
    }

    public void setFechaLimiteFormateada(String fechaLimiteFormateada) {
        this.fechaLimiteFormateada = fechaLimiteFormateada;
    }

    public long getHorasRestantes() {
        return horasRestantes;
    }

    public void setHorasRestantes(long horasRestantes) {
        this.horasRestantes = horasRestantes;
    }

    public String getEstadoSla() {
        return estadoSla;
    }

    public void setEstadoSla(String estadoSla) {
        this.estadoSla = estadoSla;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
