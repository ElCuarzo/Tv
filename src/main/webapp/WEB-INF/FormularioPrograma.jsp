<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Formulario Programa</title>
</head>
<body>
	<h1>Agregar un nuevo Programa:</h1>
	<br>
	<a href="/logout">Logout</a>
	<form:form modelAttribute="programas" action="/nueva/programa" method="POST">
	
		<form:label path="nombre" for="nombre">Nombre: </form:label>
		<form:input path="nombre" id="nombre" name="nombre" type="text"></form:input>
		<form:errors path="nombre"></form:errors>
		<br>
		<form:label path="red" for="red">Red: </form:label>
		<form:input path="red" id="red" name="red" type="text"></form:input>
		<form:errors path="red"></form:errors>
		<br>
		<form:label path="descripcion" for="descripcion">Descripción: </form:label>
		<form:input path="descripcion" id="descripcion" name="descripcion" type="text"></form:input>
		<form:errors path="descripcion"></form:errors>
		<br>
		<button>Agregar Programa</button>
		<br>
	</form:form>
	<a href="/home">Cancelar</a>
</body>
</html>