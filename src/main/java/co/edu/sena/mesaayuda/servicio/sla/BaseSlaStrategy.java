package co.edu.sena.mesaayuda.servicio.sla;

import co.edu.sena.mesaayuda.DTO.SlaDTO;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class BaseSlaStrategy implements SlaStrategy {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    @Override
    public LocalDateTime calcularFechaLimite(LocalDateTime fechaCreacion) {
        if (fechaCreacion == null) {
            fechaCreacion = LocalDateTime.now();
        }
        return fechaCreacion.plusHours(obtenerHorasMaximas());
    }

    @Override
    public SlaDTO calcularSlaInfo(LocalDateTime fechaCreacion) {
        LocalDateTime limite = calcularFechaLimite(fechaCreacion);
        LocalDateTime ahora = LocalDateTime.now();

        Duration duracionRestante = Duration.between(ahora, limite);
        long horasRestantes = duracionRestante.toHours();

        String estadoSla;
        if (ahora.isAfter(limite)) {
            estadoSla = "VENCIDO";
        } else if (horasRestantes <= 2) {
            estadoSla = "POR VENCER";
        } else {
            estadoSla = "A TIEMPO";
        }

        return new SlaDTO(
                obtenerHorasMaximas(),
                limite.format(FORMATTER),
                horasRestantes,
                estadoSla,
                obtenerDescripcion()
        );
    }
}
