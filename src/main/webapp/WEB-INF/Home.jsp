<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>home</title>
</head>
<body>
	<h3>Bienvenid@ <c:out value="${nombre}"></c:out></h3>
	<br>
	<a href="/logout">Logout</a>
	<h1>Todos los programas!</h1>
	<table>
		<thead>
			<tr>
				<th>Programa</th>
				<th>Red</th>
				<th>Promedio de Calificaciones</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${programas}" var="programa">
				<tr>
					<td><a href="/programa/${programa.id}"><c:out value="${programa.nombre}"></c:out></a></td>
					<td><c:out value="${programa.red}"></c:out></td>
					<td><c:out value="${programa.promedioCalificaciones}"></c:out></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<a href="/nueva/programa">Agregar Programa</a>
</body>
</html>