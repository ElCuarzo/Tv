<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Mostrar Canciones</title>
</head>
<body>
	<a href="/home">Volver al panel</a>
	<h1><c:out value="${programaActual.nombre}"></c:out></h1>
	<h3>Agregada por: <c:out value="${programaActual.creador}"></c:out></h3>
	<h3>Red: <c:out value="${programaActual.red}"></c:out></h3>
	<h3>Descripción:</h3>
	<p><c:out value="${programaActual.descripcion}"></c:out></p>
	<a href="/editar/programa/${programaActual.id}">Editar</a>
	<table>
	    <thead>
	        <tr>
	            <th>Usuario</th>
	            <th>Calificación</th>
	        </tr>
	    </thead>
	    <tbody>
	        <c:forEach var="calificacion" items="${programaActual.calificaciones}">
	            <tr>
	                <td>${calificacion.usuario.nombre}</td>
	                <td>${calificacion.calificacion}</td>
	            </tr>
	        </c:forEach>
	    </tbody>
	</table>
		<c:if test="${not usuarioHaCalificado}">
        <h1>Deja una calificación:</h1>
        <form:form modelAttribute="calificacionForm" action="/calificarPrograma/${programaActual.id}" method="POST">
            <label for="calificacion">Calificación:</label>
            <input type="number" id="calificacion" name="calificacion" min="1" max="5" step="0.1" required>
            <button type="submit">Califica!</button>
        </form:form>
    </c:if>
</body>
</html>