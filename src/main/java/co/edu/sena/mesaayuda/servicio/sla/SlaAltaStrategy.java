package co.edu.sena.mesaayuda.servicio.sla;

public class SlaAltaStrategy extends BaseSlaStrategy {

    @Override
    public int obtenerHorasMaximas() {
        return 8;
    }

    @Override
    public String obtenerDescripcion() {
        return "SLA Prioridad Alta (Atención máxima en 8 horas)";
    }
}
