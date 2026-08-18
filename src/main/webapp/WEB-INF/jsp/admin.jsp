<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Panel de Administración - Mesa de Ayuda CIMM</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css">
</head>
<body>

<nav class="navbar">
    <a href="${pageContext.request.contextPath}/tickets" class="navbar-brand">
        🟢 Mesa de Ayuda <span>CIMM</span> (Administración)
    </a>
    <div class="navbar-user">
        <a href="${pageContext.request.contextPath}/tickets" class="btn btn-secondary" style="padding: 4px 10px; font-size: 0.8rem;">Volver a Tickets</a>
        <a href="${pageContext.request.contextPath}/logout" class="btn-logout">Cerrar Sesión</a>
    </div>
</nav>

<div class="container">

    <div class="header-bar">
        <div class="header-title">
            <h1>Panel de Control del Administrador</h1>
            <p>Configuración de Estrategias Strategy (OCP/SOLID) y Reasignación de Tickets</p>
        </div>
    </div>

    <c:if test="${param.msg == 'reasignado'}">
        <div style="background-color: #d1fae5; color: #065f46; padding: 12px; border-radius: 8px; margin-bottom: 1.5rem; font-weight: 600;">
            ✅ Agente reasignado al ticket correctamente.
        </div>
    </c:if>

    <c:if test="${param.msg == 'estrategia_cambiada'}">
        <div style="background-color: #d1fae5; color: #065f46; padding: 12px; border-radius: 8px; margin-bottom: 1.5rem; font-weight: 600;">
            ⚙️ Estrategia de asignación de agentes actualizada con éxito.
        </div>
    </c:if>

    <!-- Configuración del Patrón Strategy para Asignación -->
    <div class="card" style="border-left: 4px solid var(--badge-asignado);">
        <h3 style="margin-bottom: 1rem; color: #0f172a;">⚙️ Configuración del Patrón Strategy (Asignación Automática de Agentes)</h3>
        <p style="color: var(--text-secondary); font-size: 0.9rem; margin-bottom: 1rem;">
            Estrategia activa actualmente: <strong style="color: var(--primary-sena-dark); font-size: 1.05rem;">${estrategiaActual}</strong>
        </p>

        <form action="${pageContext.request.contextPath}/admin/estrategia" method="post" style="display: flex; gap: 1rem; align-items: flex-end; flex-wrap: wrap;">
            <div class="form-group" style="flex: 1; min-width: 280px;">
                <label for="tipoEstrategia">Seleccionar Nueva Estrategia de Asignación:</label>
                <select name="tipoEstrategia" id="tipoEstrategia" class="form-control">
                    <option value="rotativo">Turno Rotativo (Round Robin)</option>
                    <option value="carga">Menor Carga de Trabajo Activa</option>
                    <option value="categoria">Especialidad por Categoría de Ticket</option>
                </select>
            </div>
            <button type="submit" class="btn btn-primary">Aplicar Cambio de Estrategia</button>
        </form>
    </div>

    <!-- Reasignación de Tickets -->
    <div class="card">
        <h3 style="margin-bottom: 1.25rem; color: #0f172a;">🛠️ Reasignación Manual de Agentes (RF-10)</h3>
        <div class="table-responsive">
            <table class="table">
                <thead>
                <tr>
                    <th># ID</th>
                    <th>Título</th>
                    <th>Estado</th>
                    <th>Agente Actual</th>
                    <th>Reasignar a Nuevo Agente</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="t" items="${tickets}">
                    <tr>
                        <td><strong>#${t.id}</strong></td>
                        <td>${t.titulo}</td>
                        <td><span class="badge badge-${t.estado}">${t.estado}</span></td>
                        <td>
                            <c:choose>
                                <c:when test="${not empty t.agente}">
                                    ${t.agente.nombre}
                                </c:when>
                                <c:otherwise>
                                    <span style="color: var(--text-muted);">Sin Asignar</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:if test="${t.estado != 'CERRADO' && t.estado != 'CANCELADO'}">
                                <form action="${pageContext.request.contextPath}/admin/reasignar" method="post" style="display: flex; gap: 8px;">
                                    <input type="hidden" name="ticketId" value="${t.id}">
                                    <select name="agenteId" class="form-control" style="padding: 4px 8px; font-size: 0.85rem;" required>
                                        <option value="">-- Seleccionar Agente --</option>
                                        <c:forEach var="ag" items="${agentes}">
                                            <option value="${ag.id}">${ag.nombre} (${ag.especialidad})</option>
                                        </c:forEach>
                                    </select>
                                    <button type="submit" class="btn btn-info" style="padding: 4px 10px; font-size: 0.8rem;">Reasignar</button>
                                </form>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

</div>

<footer>
    Mesa de Ayuda CIMM - SENA Regional Boyacá
</footer>

</body>
</html>
