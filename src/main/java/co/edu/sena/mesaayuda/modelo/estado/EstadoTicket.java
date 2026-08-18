package co.edu.sena.mesaayuda.modelo.estado;

/**
 * Interfaz principal del patron State para el ciclo de vida de un ticket.
 * Cada estado decide sus transiciones validas y rechaza acciones invalidas.
 */
public interface EstadoTicket {
    EstadoTicket asignar();
    EstadoTicket iniciar();
    EstadoTicket resolver();
    EstadoTicket cerrar();
    EstadoTicket reabrir();
    EstadoTicket cancelar();
    String nombre();
}
