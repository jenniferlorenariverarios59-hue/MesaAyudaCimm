package co.edu.sena.mesaayuda.servicio.sla;

public class SlaCriticaStrategy extends BaseSlaStrategy {

    @Override
    public int obtenerHorasMaximas() {
        return 2;
    }

    @Override
    public String obtenerDescripcion() {
        return "SLA Prioridad Crítica (Atención máxima en 2 horas)";
    }
}
