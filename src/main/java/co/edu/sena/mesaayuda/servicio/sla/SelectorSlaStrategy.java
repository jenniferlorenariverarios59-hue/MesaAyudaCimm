package co.edu.sena.mesaayuda.servicio.sla;

import co.edu.sena.mesaayuda.modelo.Prioridad;

import java.util.HashMap;
import java.util.Map;

public class SelectorSlaStrategy {

    private final Map<Prioridad, SlaStrategy> estrategias = new HashMap<>();

    public SelectorSlaStrategy() {
        estrategias.put(Prioridad.BAJA, new SlaBajaStrategy());
        estrategias.put(Prioridad.MEDIA, new SlaMediaStrategy());
        estrategias.put(Prioridad.ALTA, new SlaAltaStrategy());
        estrategias.put(Prioridad.CRITICA, new SlaCriticaStrategy());
    }

    public SlaStrategy obtenerEstrategia(Prioridad prioridad) {
        if (prioridad == null) {
            return estrategias.get(Prioridad.MEDIA);
        }
        return estrategias.getOrDefault(prioridad, estrategias.get(Prioridad.MEDIA));
    }
}
