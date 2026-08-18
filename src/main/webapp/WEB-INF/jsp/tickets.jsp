<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mesa de Ayuda CIMM - Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css">
</head>
<body>

<nav class="navbar">
    <a href="${pageContext.request.contextPath}/tickets" class="navbar-brand">
        🟢 Mesa de Ayuda <span>CIMM</span>
    </a>
    <div class="navbar-user">
        <span>Bienvenido, <strong>${usuarioLogueado.nombre}</strong></span>
        <span class="user-badge">${usuarioLogueado.nombreRol}</span>
        <c:if test="${usuarioLogueado.rol == 'ADMINISTRADOR'}">
            <a href="${pageContext.request.contextPath}/admin" class="btn btn-warning" style="padding: 4px 10px; font-size: 0.8rem;">Panel Admin</a>
        </c:if>
        <a href="${pageContext.request.contextPath}/logout" class="btn-logout">Cerrar Sesión</a>
    </div>
</nav>

<div class="container">

    <div class="header-bar">
        <div class="header-title">
            <h1>
                <c:choose>
                    <c:when test="${usuarioLogueado.rol == 'SOLICITANTE'}">Mis Tickets Solicitados</c:when>
                    <c:when test="${usuarioLogueado.rol == 'AGENTE'}">Tickets Asignados a Atención</c:when>
                    <c:otherwise>Gestión General de Tickets (Administración)</c:otherwise>
                </c:choose>
            </h1>
            <p>Monitoreo en tiempo real, SLA y transiciones con patrón State</p>
        </div>

        <div>
            <a href="${pageContext.request.contextPath}/tickets/crear" class="btn btn-primary">
                ➕ Crear Nuevo Ticket
            </a>
        </div>
    </div>

    <!-- Filtros de búsqueda -->
    <div class="filter-card">
        <form action="${pageContext.request.contextPath}/tickets" method="get" class="filter-form">
            <div class="form-group">
                <label for="estado">Filtrar por Estado</label>
                <select name="estado" id="estado" class="form-control" onchange="this.form.submit()">
                    <option value="">-- Todos los Estados --</option>
                    <option value="NUEVO" ${filtroEstado == 'NUEVO' ? 'selected' : ''}>NUEVO</option>
                    <option value="ASIGNADO" ${filtroEstado == 'ASIGNADO' ? 'selected' : ''}>ASIGNADO</option>
                    <option value="EN_PROCESO" ${filtroEstado == 'EN_PROCESO' ? 'selected' : ''}>EN PROCESO</option>
                    <option value="RESUELTO" ${filtroEstado == 'RESUELTO' ? 'selected' : ''}>RESUELTO</option>
                    <option value="CERRADO" ${filtroEstado == 'CERRADO' ? 'selected' : ''}>CERRADO</option>
                    <option value="CANCELADO" ${filtroEstado == 'CANCELADO' ? 'selected' : ''}>CANCELADO</option>
                </select>
            </div>

            <div class="form-group">
                <label for="categoria">Filtrar por Categoría</label>
                <select name="categoria" id="categoria" class="form-control" onchange="this.form.submit()">
                    <option value="">-- Todas las Categorías --</option>
                    <c:forEach var="cat" items="${categorias}">
                        <option value="${cat.name()}" ${filtroCategoria == cat.name() ? 'selected' : ''}>${cat.nombreMostrar}</option>
                    </c:forEach>
                </select>
            </div>

            <div style="align-self: flex-end;">
                <a href="${pageContext.request.contextPath}/tickets" class="btn btn-secondary">Limpiar Filtros</a>
            </div>
        </form>
    </div>

    <!-- Tabla de Tickets -->
    <div class="card">
        <div class="table-responsive">
            <table class="table">
                <thead>
                <tr>
                    <th># ID</th>
                    <th>Título</th>
                    <th>Categoría</th>
                    <th>Prioridad</th>
                    <th>SLA (Límite)</th>
                    <th>Solicitante</th>
                    <th>Agente Responsable</th>
                    <th>Estado</th>
                    <th>Acción</th>
                </tr>
                </thead>
                <tbody>
                <c:choose>
                    <c:when test="${empty tickets}">
                        <tr>
                            <td colspan="9" style="text-align: center; color: var(--text-muted); padding: 2rem;">
                                No hay tickets registrados que coincidan con el criterio seleccionado.
                            </td>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="t" items="${tickets}">
                            <tr>
                                <td><strong>#${t.id}</strong></td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/tickets/detalle?id=${t.id}" style="color: #0284c7; font-weight: 600; text-decoration: none;">
                                        ${t.titulo}
                                    </a>
                                </td>
                                <td>${t.nombreCategoria}</td>
                                <td>
                                    <span style="font-weight: 600;">${t.nombrePrioridad}</span>
                                </td>
                                <td>
                                    <span class="sla-badge sla-${t.slaInfo.estadoSla}">
                                        ⏱️ ${t.slaInfo.estadoSla} (${t.slaInfo.fechaLimiteFormateada})
                                    </span>
                                </td>
                                <td>${t.solicitante.nombre}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${not empty t.agente}">
                                            🛠️ ${t.agente.nombre}
                                        </c:when>
                                        <c:otherwise>
                                            <span style="color: var(--text-muted); font-style: italic;">Sin asignar</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <span class="badge badge-${t.estado}">${t.estado}</span>
                                </td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/tickets/detalle?id=${t.id}" class="btn btn-info" style="padding: 4px 10px; font-size: 0.8rem;">
                                        Ver Detalle
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
                </tbody>
            </table>
        </div>
    </div>
</div>

<footer>
    Mesa de Ayuda CIMM - SENA Regional Boyacá | Aplicación construida con Servlets y Patrones SOLID
</footer>

</body>
</html>
