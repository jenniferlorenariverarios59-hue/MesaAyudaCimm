package co.edu.sena.mesaayuda.modelo;

public enum Prioridad {
    BAJA("Baja", 48),
    MEDIA("Media", 24),
    ALTA("Alta", 8),
    CRITICA("Crítica", 2);

    private final String nombreMostrar;
    private final int horasMaximasSla;

    Prioridad(String nombreMostrar, int horasMaximasSla) {
        this.nombreMostrar = nombreMostrar;
        this.horasMaximasSla = horasMaximasSla;
    }

    public String getNombreMostrar() {
        return nombreMostrar;
    }

    public int getHorasMaximasSla() {
        return horasMaximasSla;
    }
}
