package co.edu.sena.mesaayuda.servicio.sla;

import co.edu.sena.mesaayuda.DTO.SlaDTO;

import java.time.LocalDateTime;

public interface SlaStrategy {
    int obtenerHorasMaximas();
    LocalDateTime calcularFechaLimite(LocalDateTime fechaCreacion);
    String obtenerDescripcion();
    SlaDTO calcularSlaInfo(LocalDateTime fechaCreacion);
}
