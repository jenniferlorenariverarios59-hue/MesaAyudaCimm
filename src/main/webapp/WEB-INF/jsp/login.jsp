<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Iniciar Sesión - Mesa de Ayuda CIMM</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css">
</head>
<body class="login-body">

<div class="login-card">
    <div class="login-header">
        <h2>Mesa de Ayuda <span>CIMM</span></h2>
        <p>Sistema de Tickets de Soporte Técnico SENA</p>
    </div>

    <c:if test="${not empty error}">
        <div style="background-color: #fee2e2; color: #991b1b; padding: 10px; border-radius: 6px; margin-bottom: 1rem; font-size: 0.9rem;">
            ${error}
        </div>
    </c:if>

    <c:if test="${param.msg == 'sesion_cerrada'}">
        <div style="background-color: #d1fae5; color: #065f46; padding: 10px; border-radius: 6px; margin-bottom: 1rem; font-size: 0.9rem;">
            Has cerrado sesión correctamente.
        </div>
    </c:if>

    <form action="${pageContext.request.contextPath}/login" method="post">
        <div class="form-group" style="margin-bottom: 1rem;">
            <label for="correo">Correo Electrónico</label>
            <input type="email" id="correo" name="correo" class="form-control" required value="${correoIngresado != null ? correoIngresado : ''}" placeholder="ejemplo@sena.edu.co">
        </div>

        <div class="form-group" style="margin-bottom: 1.5rem;">
            <label for="password">Contraseña</label>
            <input type="password" id="password" name="password" class="form-control" required placeholder="••••••••">
        </div>

        <button type="submit" class="btn btn-primary" style="width: 100%; padding: 10px;">Ingresar al Sistema</button>
    </form>

    <div class="quick-login">
        <h4>Acceso Rápido de Prueba (Hacer clic)</h4>
        <div class="quick-login-buttons">
            <button class="btn-demo" onclick="llenarYEnviar('juan.perez@sena.edu.co', '123456')">
                👤 <strong>Solicitante:</strong> juan.perez@sena.edu.co
            </button>
            <button class="btn-demo" onclick="llenarYEnviar('carlos.redes@sena.edu.co', '123456')">
                🛠️ <strong>Agente (Redes):</strong> carlos.redes@sena.edu.co
            </button>
            <button class="btn-demo" onclick="llenarYEnviar('admin@sena.edu.co', 'admin123')">
                ⚙️ <strong>Administrador:</strong> admin@sena.edu.co
            </button>
        </div>
    </div>
</div>

<script>
    function llenarYEnviar(correo, pwd) {
        document.getElementById('correo').value = correo;
        document.getElementById('password').value = pwd;
        document.forms[0].submit();
    }
</script>

</body>
</html>
