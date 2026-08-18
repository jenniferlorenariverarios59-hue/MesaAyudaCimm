<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Crear Nuevo Ticket - Mesa de Ayuda CIMM</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css">
</head>
<body>

<nav class="navbar">
    <a href="${pageContext.request.contextPath}/tickets" class="navbar-brand">
        🟢 Mesa de Ayuda <span>CIMM</span>
    </a>
    <div class="navbar-user">
        <a href="${pageContext.request.contextPath}/tickets" class="btn btn-secondary" style="padding: 4px 10px; font-size: 0.8rem;">Volver a Tickets</a>
    </div>
</nav>

<div class="container" style="max-width: 700px;">

    <div class="card">
        <h2 style="margin-bottom: 1rem; color: #0f172a; border-bottom: 2px solid var(--primary-sena); padding-bottom: 0.5rem;">
            Registrar Solicitud de Soporte Técnico
        </h2>
        <p style="color: var(--text-secondary); font-size: 0.9rem; margin-bottom: 1.5rem;">
            Complete los detalles del problema. El sistema asignará automáticamente la prioridad (SLA) y el agente responsable según las reglas de negocio configuradas.
        </p>

        <c:if test="${not empty error}">
            <div style="background-color: #fee2e2; color: #991b1b; padding: 10px; border-radius: 6px; margin-bottom: 1rem;">
                ${error}
            </div>
        </c:if>

        <form action="${pageContext.request.contextPath}/tickets/crear" method="post">
            <div class="form-group" style="margin-bottom: 1.25rem;">
                <label for="titulo">Título de la Solicitud *</label>
                <input type="text" id="titulo" name="titulo" class="form-control" required placeholder="Ej: Falla en equipo de laboratorio / Caída de red Wi-Fi">
            </div>

            <div class="form-group" style="margin-bottom: 1.25rem;">
                <label for="categoria">Categoría del Problema *</label>
                <select name="categoria" id="categoria" class="form-control" required>
                    <option value="">-- Seleccione una Categoría --</option>
                    <c:forEach var="cat" items="${categorias}">
                        <option value="${cat.name()}">${cat.nombreMostrar}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group" style="margin-bottom: 1.5rem;">
                <label for="descripcion">Descripción Detallada del Problema *</label>
                <textarea id="descripcion" name="descripcion" class="form-control" rows="5" required placeholder="Describa qué ocurrió, ubicación o código de equipo, si es urgente, etc..."></textarea>
            </div>

            <div style="display: flex; justify-content: flex-end; gap: 1rem;">
                <a href="${pageContext.request.contextPath}/tickets" class="btn btn-secondary">Cancelar</a>
                <button type="submit" class="btn btn-primary">➕ Registrar Ticket</button>
            </div>
        </form>
    </div>

</div>

<footer>
    Mesa de Ayuda CIMM - SENA Regional Boyacá
</footer>

</body>
</html>
