package co.edu.sena.mesaayuda.modelo;

public enum Rol {
    SOLICITANTE("Solicitante"),
    AGENTE("Agente de Soporte"),
    ADMINISTRADOR("Administrador");

    private final String nombreMostrar;

    Rol(String nombreMostrar) {
        this.nombreMostrar = nombreMostrar;
    }

    public String getNombreMostrar() {
        return nombreMostrar;
    }
}
