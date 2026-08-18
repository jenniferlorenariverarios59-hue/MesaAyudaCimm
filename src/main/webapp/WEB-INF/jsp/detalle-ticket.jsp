<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Detalle del Ticket #${ticket.id} - Mesa de Ayuda CIMM</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css">
</head>
<body>

<nav class="navbar">
    <a href="${pageContext.request.contextPath}/tickets" class="navbar-brand">
        🟢 Mesa de Ayuda <span>CIMM</span>
    </a>
    <div class="navbar-user">
        <span>Usuario: <strong>${usuarioLogueado.nombre}</strong> (${usuarioLogueado.nombreRol})</span>
        <a href="${pageContext.request.contextPath}/tickets" class="btn btn-secondary" style="padding: 4px 10px; font-size: 0.8rem;">Volver a la Lista</a>
    </div>
</nav>

<div class="container">

    <c:if test="${param.msg == 'creado'}">
        <div style="background-color: #d1fae5; color: #065f46; padding: 12px; border-radius: 8px; margin-bottom: 1.5rem; font-weight: 600;">
            ✅ Ticket registrado correctamente y asignado automáticamente.
        </div>
    </c:if>

    <c:if test="${not empty param.error}">
        <div style="background-color: #fee2e2; color: #991b1b; padding: 12px; border-radius: 8px; margin-bottom: 1.5rem; font-weight: 600;">
            ⚠️ ${param.error}
        </div>
    </c:if>

    <!-- Header info del ticket -->
    <div class="card">
        <div style="display: flex; justify-content: space-between; align-items: flex-start; flex-wrap: wrap; gap: 1rem; margin-bottom: 1rem;">
            <div>
                <span class="badge badge-${ticket.estado}" style="font-size: 0.9rem; margin-bottom: 8px;">Estado actual: ${ticket.estado}</span>
                <h1 style="font-size: 1.75rem; color: #0f172a; margin-top: 4px;">#${ticket.id} - ${ticket.titulo}</h1>
                <p style="color: var(--text-secondary); font-size: 0.9rem;">
                    Solicitado por <strong>${ticket.solicitante.nombre}</strong> (${ticket.solicitante.correo}) el ${ticket.fechaCreacionFormateada}
                </p>
            </div>

            <div class="sla-badge sla-${ticket.slaInfo.estadoSla}" style="font-size: 0.95rem; padding: 8px 14px;">
                ⏱️ SLA ${ticket.slaInfo.estadoSla} | Límite: ${ticket.slaInfo.fechaLimiteFormateada}
            </div>
        </div>

        <div style="display: grid; grid-template-columns: repeat(auto-fit, minmax(220px, 1fr)); gap: 1rem; background: #f8fafc; padding: 1rem; border-radius: 8px; margin-bottom: 1.5rem;">
            <div>
                <span style="font-size: 0.8rem; color: var(--text-muted); display: block;">Categoría</span>
                <strong>${ticket.nombreCategoria}</strong>
            </div>
            <div>
                <span style="font-size: 0.8rem; color: var(--text-muted); display: block;">Prioridad Calculada</span>
                <strong>${ticket.nombrePrioridad} (${ticket.slaInfo.horasMaximas}h SLA)</strong>
            </div>
            <div>
                <span style="font-size: 0.8rem; color: var(--text-muted); display: block;">Agente Asignado</span>
                <strong>
                    <c:choose>
                        <c:when test="${not empty ticket.agente}">
                            🛠️ ${ticket.agente.nombre} (${ticket.agente.correo})
                        </c:when>
                        <c:otherwise>
                            <span style="color: var(--text-muted);">Sin asignar</span>
                        </c:otherwise>
                    </c:choose>
                </strong>
            </div>
            <div>
                <span style="font-size: 0.8rem; color: var(--text-muted); display: block;">Última Actualización</span>
                <strong>${ticket.fechaActualizacionFormateada}</strong>
            </div>
        </div>

        <div>
            <h4 style="margin-bottom: 8px; color: var(--text-secondary);">Descripción del Problema:</h4>
            <p style="background: white; padding: 1rem; border-radius: 6px; border: 1px solid var(--border-color); white-space: pre-wrap; font-size: 0.95rem;">${ticket.descripcion}</p>
        </div>

        <!-- Botones de Acción (Respetando el patrón State y Rol de Usuario) -->
        <div style="margin-top: 2rem; padding-top: 1.5rem; border-top: 1px solid var(--border-color);">
            <h4 style="margin-bottom: 1rem; color: var(--text-secondary);">Transiciones del Estado (Patrón State):</h4>
            <div style="display: flex; gap: 1rem; flex-wrap: wrap;">

                <!-- Iniciar Atención (Agente) -->
                <c:if test="${ticket.puedeIniciar && (usuarioLogueado.rol == 'AGENTE' || usuarioLogueado.rol == 'ADMINISTRADOR')}">
                    <form action="${pageContext.request.contextPath}/tickets/accion" method="post">
                        <input type="hidden" name="ticketId" value="${ticket.id}">
                        <input type="hidden" name="accion" value="iniciar">
                        <button type="submit" class="btn btn-warning">▶️ Iniciar Atención (En Proceso)</button>
                    </form>
                </c:if>

                <!-- Resolver Ticket (Agente) -->
                <c:if test="${ticket.puedeResolver && (usuarioLogueado.rol == 'AGENTE' || usuarioLogueado.rol == 'ADMINISTRADOR')}">
                    <form action="${pageContext.request.contextPath}/tickets/accion" method="post">
                        <input type="hidden" name="ticketId" value="${ticket.id}">
                        <input type="hidden" name="accion" value="resolver">
                        <button type="submit" class="btn btn-primary">✅ Marcar como Resuelto</button>
                    </form>
                </c:if>

                <!-- Confirmar Cierre (Solicitante / Admin) -->
                <c:if test="${ticket.puedeCerrar && (usuarioLogueado.rol == 'SOLICITANTE' || usuarioLogueado.rol == 'ADMINISTRADOR')}">
                    <form action="${pageContext.request.contextPath}/tickets/accion" method="post">
                        <input type="hidden" name="ticketId" value="${ticket.id}">
                        <input type="hidden" name="accion" value="cerrar">
                        <button type="submit" class="btn btn-secondary">🔒 Confirmar Cierre Definitivo</button>
                    </form>
                </c:if>

                <!-- Reabrir Ticket (Solicitante / Admin) -->
                <c:if test="${ticket.puedeReabrir && (usuarioLogueado.rol == 'SOLICITANTE' || usuarioLogueado.rol == 'ADMINISTRADOR')}">
                    <form action="${pageContext.request.contextPath}/tickets/accion" method="post">
                        <input type="hidden" name="ticketId" value="${ticket.id}">
                        <input type="hidden" name="accion" value="reabrir">
                        <button type="submit" class="btn btn-warning">🔄 Reabrir Ticket (En Proceso)</button>
                    </form>
                </c:if>

                <!-- Cancelar Ticket (Solo Admin) -->
                <c:if test="${ticket.puedeCancelar && usuarioLogueado.rol == 'ADMINISTRADOR'}">
                    <form action="${pageContext.request.contextPath}/tickets/accion" method="post" onsubmit="return confirm('¿Está seguro de cancelar este ticket?');">
                        <input type="hidden" name="ticketId" value="${ticket.id}">
                        <input type="hidden" name="accion" value="cancelar">
                        <button type="submit" class="btn btn-danger">❌ Cancelar Ticket (Admin)</button>
                    </form>
                </c:if>

            </div>
        </div>
    </div>

    <!-- Sección de Comentarios (RF-07) -->
    <div class="card comments-section">
        <h3 style="margin-bottom: 1.25rem; color: #0f172a;">Historial de Comentarios y Seguimiento</h3>

        <c:choose>
            <c:when test="${empty ticket.comentarios}">
                <p style="color: var(--text-muted); font-style: italic; margin-bottom: 1.5rem;">No hay comentarios en este ticket aún.</p>
            </c:when>
            <c:otherwise>
                <c:forEach var="c" items="${ticket.comentarios}">
                    <div class="comment-card">
                        <div class="comment-header">
                            <span class="comment-author">
                                ${c.autor.nombre} <span class="user-badge" style="font-size: 0.7rem; padding: 2px 6px;">${c.autor.nombreRol}</span>
                            </span>
                            <span>${c.fechaFormateada}</span>
                        </div>
                        <div class="comment-text">
                            ${c.texto}
                        </div>
                    </div>
                </c:forEach>
            </c:otherwise>
        </c:choose>

        <!-- Formulario Agregar Comentario -->
        <c:if test="${ticket.estado != 'CERRADO' && ticket.estado != 'CANCELADO'}">
            <form action="${pageContext.request.contextPath}/tickets/accion" method="post" style="margin-top: 1.5rem;">
                <input type="hidden" name="ticketId" value="${ticket.id}">
                <input type="hidden" name="accion" value="comentar">
                <div class="form-group" style="margin-bottom: 1rem;">
                    <label for="textoComentario">Agregar Comentario al Ticket</label>
                    <textarea id="textoComentario" name="textoComentario" class="form-control" rows="3" required placeholder="Escriba un comentario o actualización sobre el avance..."></textarea>
                </div>
                <button type="submit" class="btn btn-primary">💬 Publicar Comentario</button>
            </form>
        </c:if>
    </div>

</div>

<footer>
    Mesa de Ayuda CIMM - SENA Regional Boyacá
</footer>

</body>
</html>
