package co.edu.sena.mesaayuda.servicio.sla;

public class SlaBajaStrategy extends BaseSlaStrategy {

    @Override
    public int obtenerHorasMaximas() {
        return 48;
    }

    @Override
    public String obtenerDescripcion() {
        return "SLA Prioridad Baja (Atención máxima en 48 horas)";
    }
}
