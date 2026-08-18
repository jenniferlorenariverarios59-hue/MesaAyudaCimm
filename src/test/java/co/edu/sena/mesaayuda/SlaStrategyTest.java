package co.edu.sena.mesaayuda;

import co.edu.sena.mesaayuda.dto.SlaDTO;
import co.edu.sena.mesaayuda.modelo.Prioridad;
import co.edu.sena.mesaayuda.servicio.sla.SelectorSlaStrategy;
import co.edu.sena.mesaayuda.servicio.sla.SlaStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class SlaStrategyTest {

    private SelectorSlaStrategy selectorSla;

    @BeforeEach
    void setUp() {
        selectorSla = new SelectorSlaStrategy();
    }

    @Test
    @DisplayName("SLA Prioridad BAJA debe ser de 48 horas")
    void testSlaBaja() {
        SlaStrategy strategy = selectorSla.obtenerEstrategia(Prioridad.BAJA);
        assertEquals(48, strategy.obtenerHorasMaximas());
    }

    @Test
    @DisplayName("SLA Prioridad MEDIA debe ser de 24 horas")
    void testSlaMedia() {
        SlaStrategy strategy = selectorSla.obtenerEstrategia(Prioridad.MEDIA);
        assertEquals(24, strategy.obtenerHorasMaximas());
    }

    @Test
    @DisplayName("SLA Prioridad ALTA debe ser de 8 horas")
    void testSlaAlta() {
        SlaStrategy strategy = selectorSla.obtenerEstrategia(Prioridad.ALTA);
        assertEquals(8, strategy.obtenerHorasMaximas());
    }

    @Test
    @DisplayName("SLA Prioridad CRITICA debe ser de 2 horas")
    void testSlaCritica() {
        SlaStrategy strategy = selectorSla.obtenerEstrategia(Prioridad.CRITICA);
        assertEquals(2, strategy.obtenerHorasMaximas());
    }

    @Test
    @DisplayName("Cálculo de SlaDTO a tiempo")
    void testCalculoSlaATiempo() {
        SlaStrategy strategy = selectorSla.obtenerEstrategia(Prioridad.MEDIA);
        LocalDateTime ahora = LocalDateTime.now();

        SlaDTO slaInfo = strategy.calcularSlaInfo(ahora);
        assertEquals(24, slaInfo.getHorasMaximas());
        assertEquals("A TIEMPO", slaInfo.getEstadoSla());
    }

    @Test
    @DisplayName("Cálculo de SlaDTO vencido para ticket antiguo")
    void testCalculoSlaVencido() {
        SlaStrategy strategy = selectorSla.obtenerEstrategia(Prioridad.CRITICA);
        LocalDateTime ticketAntiguo = LocalDateTime.now().minusHours(10);

        SlaDTO slaInfo = strategy.calcularSlaInfo(ticketAntiguo);
        assertEquals("VENCIDO", slaInfo.getEstadoSla());
    }
}
