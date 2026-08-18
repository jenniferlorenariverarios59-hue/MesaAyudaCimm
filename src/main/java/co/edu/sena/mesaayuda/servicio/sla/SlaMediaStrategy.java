package co.edu.sena.mesaayuda.servicio.sla;

public class SlaMediaStrategy extends BaseSlaStrategy {

    @Override
    public int obtenerHorasMaximas() {
        return 24;
    }

    @Override
    public String obtenerDescripcion() {
        return "SLA Prioridad Media (Atención máxima en 24 horas)";
    }
}
