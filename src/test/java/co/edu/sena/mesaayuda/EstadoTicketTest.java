package co.edu.sena.mesaayuda;

import co.edu.sena.mesaayuda.modelo.Ticket;
import co.edu.sena.mesaayuda.modelo.Usuario;
import co.edu.sena.mesaayuda.modelo.Rol;
import co.edu.sena.mesaayuda.modelo.estado.AsignadoState;
import co.edu.sena.mesaayuda.modelo.estado.CerradoState;
import co.edu.sena.mesaayuda.modelo.estado.EnProcesoState;
import co.edu.sena.mesaayuda.modelo.estado.NuevoState;
import co.edu.sena.mesaayuda.modelo.estado.ResueltoState;
import co.edu.sena.mesaayuda.modelo.estado.TransicionEstadoInvalidaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EstadoTicketTest {

    private Ticket ticket;
    private Usuario agente;

    @BeforeEach
    void setUp() {
        ticket = new Ticket();
        agente = new Usuario(1L, "Agente Pruebas", "agente@sena.edu.co", "123", Rol.AGENTE);
    }

    @Test
    @DisplayName("Estado inicial del ticket debe ser NUEVO")
    void testEstadoInicial() {
        assertEquals("NUEVO", ticket.getEstado().nombre());
        assertTrue(ticket.getEstado() instanceof NuevoState);
    }

    @Test
    @DisplayName("Transiciones válidas del ciclo de vida: NUEVO -> ASIGNADO -> EN_PROCESO -> RESUELTO -> CERRADO")
    void testFlujoCompletoValido() {
        // 1. Asignar agente (NUEVO -> ASIGNADO)
        ticket.asignarAgente(agente);
        assertEquals("ASIGNADO", ticket.getEstado().nombre());
        assertTrue(ticket.getEstado() instanceof AsignadoState);
        assertEquals(agente, ticket.getAgente());

        // 2. Iniciar atención (ASIGNADO -> EN_PROCESO)
        ticket.iniciarAtencion();
        assertEquals("EN_PROCESO", ticket.getEstado().nombre());
        assertTrue(ticket.getEstado() instanceof EnProcesoState);

        // 3. Resolver ticket (EN_PROCESO -> RESUELTO)
        ticket.resolver();
        assertEquals("RESUELTO", ticket.getEstado().nombre());
        assertTrue(ticket.getEstado() instanceof ResueltoState);

        // 4. Confirmar cierre (RESUELTO -> CERRADO)
        ticket.cerrar();
        assertEquals("CERRADO", ticket.getEstado().nombre());
        assertTrue(ticket.getEstado() instanceof CerradoState);
    }

    @Test
    @DisplayName("Intentar cerrar un ticket NUEVO debe lanzar TransicionEstadoInvalidaException")
    void testTransicionInvalidaCerrarDesdeNuevo() {
        assertThrows(TransicionEstadoInvalidaException.class, () -> ticket.cerrar());
    }

    @Test
    @DisplayName("Intentar resolver un ticket ASIGNADO sin iniciar atención debe lanzar TransicionEstadoInvalidaException")
    void testTransicionInvalidaResolverDesdeAsignado() {
        ticket.asignarAgente(agente);
        assertThrows(TransicionEstadoInvalidaException.class, () -> ticket.resolver());
    }

    @Test
    @DisplayName("Transición válida de reabrir ticket (RESUELTO -> EN_PROCESO)")
    void testReabrirTicket() {
        ticket.asignarAgente(agente);
        ticket.iniciarAtencion();
        ticket.resolver();
        assertEquals("RESUELTO", ticket.getEstado().nombre());

        ticket.reabrir();
        assertEquals("EN_PROCESO", ticket.getEstado().nombre());
    }
}
