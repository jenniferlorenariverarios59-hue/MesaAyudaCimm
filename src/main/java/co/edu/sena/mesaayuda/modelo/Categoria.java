package co.edu.sena.mesaayuda.modelo;

public enum Categoria {
    RED("Redes y Conectividad"),
    HARDWARE("Equipos y Hardware"),
    SOFTWARE("Sistemas y Software"),
    MANTENIMIENTO("Mantenimiento Técnico"),
    OTRO("General / Otro");

    private final String nombreMostrar;

    Categoria(String nombreMostrar) {
        this.nombreMostrar = nombreMostrar;
    }

    public String getNombreMostrar() {
        return nombreMostrar;
    }
}
