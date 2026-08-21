package co.edu.sena.mesaayuda.web;

import co.edu.sena.mesaayuda.modelo.Categoria;
import co.edu.sena.mesaayuda.modelo.Rol;
import co.edu.sena.mesaayuda.modelo.Usuario;
import co.edu.sena.mesaayuda.repositorio.TicketRepository;
import co.edu.sena.mesaayuda.repositorio.TicketRepositoryEnMemoria;
import co.edu.sena.mesaayuda.repositorio.UsuarioRepository;
import co.edu.sena.mesaayuda.repositorio.UsuarioRepositoryEnMemoria;
import co.edu.sena.mesaayuda.servicio.AuthService;
import co.edu.sena.mesaayuda.servicio.AuthServiceImpl;
import co.edu.sena.mesaayuda.servicio.TicketService;
import co.edu.sena.mesaayuda.servicio.TicketServiceImpl;
import co.edu.sena.mesaayuda.servicio.asignacion.AsignacionStrategy;
import co.edu.sena.mesaayuda.servicio.asignacion.TurnoRotativoStrategy;
import co.edu.sena.mesaayuda.servicio.notificacion.AppNotificador;
import co.edu.sena.mesaayuda.servicio.notificacion.EmailNotificador;
import co.edu.sena.mesaayuda.servicio.notificacion.MultiNotificador;
import co.edu.sena.mesaayuda.servicio.notificacion.Notificador;
import co.edu.sena.mesaayuda.servicio.notificacion.SmsNotificador;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.List;

/**
 * Composition Root de la aplicación Mesa de Ayuda CIMM.
 * Aquí se instancian e inyectan manualmente todas las dependencias (DIP)
 * y se cargan los datos semilla de prueba.
 */
@WebListener
public class AppContextListener implements ServletContextListener {

    public static final String AUTH_SERVICE = "authService";
    public static final String TICKET_SERVICE = "ticketService";
    public static final String NOTIFICADOR_SERVICE = "notificadorService";
    public static final String APP_NOTIFICADOR = "appNotificador";


    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext contexto = event.getServletContext();

        // 1. Repositorios
        UsuarioRepository usuarioRepository = new UsuarioRepositoryEnMemoria();
        TicketRepository ticketRepository = new TicketRepositoryEnMemoria();

        // 2. Estrategia de Asignación por defecto
        AsignacionStrategy asignacionStrategy = new TurnoRotativoStrategy();

        // 3. Sistema de Notificaciones con patrón Composite (OCP)
        AppNotificador appNotificador = new AppNotificador();

        Notificador notificador = new MultiNotificador(List.of(
                new EmailNotificador(),
                new SmsNotificador(),
                appNotificador
        ));


        // 4. Servicios con inyección de dependencias por constructor (DIP / SRP)
        AuthService authService = new AuthServiceImpl(usuarioRepository);
        TicketService ticketService = new TicketServiceImpl(
                ticketRepository, usuarioRepository, asignacionStrategy, notificador
        );

        // 5. Cargar datos semilla para pruebas iniciales
        cargarDatosSemilla(usuarioRepository, ticketService);

        // 6. Publicar servicios en ServletContext
        contexto.setAttribute(AUTH_SERVICE, authService);
        contexto.setAttribute(TICKET_SERVICE, ticketService);
        contexto.setAttribute(NOTIFICADOR_SERVICE, notificador);
        contexto.setAttribute(APP_NOTIFICADOR, appNotificador);


        System.out.println(">>> AppContextListener: Mesa de Ayuda CIMM inicializada correctamente.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        System.out.println(">>> AppContextListener: Contexto destruido.");
    }

    private void cargarDatosSemilla(UsuarioRepository usuarioRepo, TicketService ticketService) {
        // Solicitantes
        Usuario sol1 = usuarioRepo.guardar(new Usuario(null, "Juan Pérez (Aprendiz)", "juan.perez@sena.edu.co", "123456", Rol.SOLICITANTE));
        Usuario sol2 = usuarioRepo.guardar(new Usuario(null, "María Gómez (Instructora)", "maria.gomez@sena.edu.co", "123456", Rol.SOLICITANTE));

        // Agentes de Soporte con Especialidad
        usuarioRepo.guardar(new Usuario(null, "Carlos Redes", "carlos.redes@sena.edu.co", "123456", Rol.AGENTE, Categoria.RED.name()));
        usuarioRepo.guardar(new Usuario(null, "Ana Hardware", "ana.hardware@sena.edu.co", "123456", Rol.AGENTE, Categoria.HARDWARE.name()));
        usuarioRepo.guardar(new Usuario(null, "Pedro Software", "pedro.software@sena.edu.co", "123456", Rol.AGENTE, Categoria.SOFTWARE.name()));

        // Administrador
        usuarioRepo.guardar(new Usuario(null, "Administrador CIMM", "admin@sena.edu.co", "admin123", Rol.ADMINISTRADOR));

        // Creación de tickets de prueba iniciales
        ticketService.crearTicket(
                "Falla en la red Wi-Fi del Lab 2",
                "El servidor de laboratorio no responde y la red sin red general está caída de manera urgente.",
                "RED",
                sol1.getId()
        );

        ticketService.crearTicket(
                "Instalación de Software Java IDE",
                "Se requiere instalar NetBeans y Java 17 en los 20 equipos del ambiente de formación ADSO.",
                "SOFTWARE",
                sol2.getId()
        );

        var ticket3 = ticketService.crearTicket(
                "Mantenimiento preventivo impresora laser",
                "La impresora del área administrativa presenta atascos continuos de papel.",
                "MANTENIMIENTO",
                sol1.getId()
        );

        ticketService.agregarComentario(ticket3.getId(), sol1.getId(), "Por favor atender antes de medio día.");
    }
}
