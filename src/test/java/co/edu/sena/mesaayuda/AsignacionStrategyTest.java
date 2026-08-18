package co.edu.sena.mesaayuda;

import co.edu.sena.mesaayuda.modelo.Categoria;
import co.edu.sena.mesaayuda.modelo.Rol;
import co.edu.sena.mesaayuda.modelo.Ticket;
import co.edu.sena.mesaayuda.modelo.Usuario;
import co.edu.sena.mesaayuda.servicio.asignacion.AsignacionStrategy;
import co.edu.sena.mesaayuda.servicio.asignacion.CargaMinimaStrategy;
import co.edu.sena.mesaayuda.servicio.asignacion.PorCategoriaStrategy;
import co.edu.sena.mesaayuda.servicio.asignacion.TurnoRotativoStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AsignacionStrategyTest {

    private List<Usuario> agentes;
    private Usuario agente1;
    private Usuario agente2;

    @BeforeEach
    void setUp() {
        agente1 = new Usuario(1L, "Carlos Redes", "carlos@sena.edu.co", "123", Rol.AGENTE, "RED");
        agente2 = new Usuario(2L, "Ana Hardware", "ana@sena.edu.co", "123", Rol.AGENTE, "HARDWARE");

        agentes = List.of(agente1, agente2);
    }

    @Test
    @DisplayName("Estrategia TurnoRotativo debe alternar agentes secuencialmente")
    void testTurnoRotativo() {
        AsignacionStrategy strategy = new TurnoRotativoStrategy();
        Ticket t1 = new Ticket();
        Ticket t2 = new Ticket();

        Usuario seleccionado1 = strategy.seleccionarAgente(agentes, t1, new ArrayList<>());
        Usuario seleccionado2 = strategy.seleccionarAgente(agentes, t2, new ArrayList<>());

        assertEquals(agente1, seleccionado1);
        assertEquals(agente2, seleccionado2);
    }

    @Test
    @DisplayName("Estrategia PorCategoria debe asignar según la especialidad del agente")
    void testPorCategoria() {
        AsignacionStrategy strategy = new PorCategoriaStrategy();

        Ticket ticketRed = new Ticket();
        ticketRed.setCategoria(Categoria.RED);

        Usuario seleccionado = strategy.seleccionarAgente(agentes, ticketRed, new ArrayList<>());
        assertEquals(agente1, seleccionado);
        assertEquals("RED", seleccionado.getEspecialidad());
    }

    @Test
    @DisplayName("Estrategia CargaMinima debe seleccionar el agente con menos tickets activos")
    void testCargaMinima() {
        AsignacionStrategy strategy = new CargaMinimaStrategy();

        Ticket t1 = new Ticket();
        t1.asignarAgente(agente1);
        t1.iniciarAtencion(); // Ahora en estado EN_PROCESO (válido según el patrón State)

        List<Ticket> todos = List.of(t1);

        Ticket nuevoTicket = new Ticket();
        Usuario seleccionado = strategy.seleccionarAgente(agentes, nuevoTicket, todos);

        assertEquals(agente2, seleccionado, "Debe seleccionar a agente2 porque agente1 tiene 1 ticket activo");
    }
}
