<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="com.desafio.logistiqal.model.Producto"%>
<%@page import="com.desafio.logistiqal.vo.ProductoVO"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="charset=ISO-8859-1">
<script src="/webjars/jquery/jquery.min.js"></script>
<script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
<link rel="stylesheet"
	href="/webjars/bootstrap/4.3.0/css/bootstrap.min.css" />
<title>LOGISTIQAL</title>
</head>
<body onload="javascript:clearInput()">
	<!-- Inicio Header -->
	<nav class="navbar navbar-light bg-light">
		<a class="navbar-brand" href="/">LOGISTIQAL</a>
		<div class="navbar">
			<div class="navbar-nav">
				<!-- welcome user name section -->
				<!-- 				<form action="handleLogout" class="form-inline" method="POST"> -->
				<!-- 					<a class="nav-item nav-link disabled mr-sm-2" href="#" -->
				<%-- 						tabindex="-1" aria-disabled="true">${usuarioConectado} </a> <input --%>
				<!-- 						type="submit" class="btn btn-outline-danger my-2 mysm-0" -->
				<!-- 						name="btnEnviar" value="Cerrar sesión"> -->
				<!-- 
								</form> -->
				<div>
					<form class="form-inline" id="buscador"
						modelAttribute="productoBuscado" action="home" method="get">
						<input class="form-control mr-sm-2" id="search" type="text"
							name="busqueda" placeholder="buscar productos"
							aria-label="Search" value="${busqueda}">

						<button class="btn btn-outline-success my-2 my-sm-0"
							value="Buscar" type="submit">Buscar</button>

					</form>
				</div>

			</div>
		</div>
	</nav>
	<!-- Fin Header -->
	<!-- Inicio Contenido -->
	<div class="p-3">
		<!-- Inicio Mensajes -->
		<c:if test="${mensaje != null ? true : false}">
			<div class="alert alert-secondary alert-dismissible fade show"
				role="alert">${mensaje}
				<button type="button" class="close" data-dismiss="alert"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
		</c:if>
		<!-- Fin Mensajes -->

		<div style="padding-left: 5%; padding-right: 5%;">
			<!-- Botón agregar usuario -->
			<a style="display: inline-block;" href="agregarForm"
				class="btn m-2 btn-success">Agregar Producto</a>

				<div style="display:inline-block; float:right">
				<span>Registros por pantalla:</span>
				<a name="registrosPorPagina" value="3" type="submit" href="home?busqueda=${busqueda}&p=${pagina}&registrosPorPagina=3">3</a>
				<a name="registrosPorPagina" value="5" type="submit" href="home?busqueda=${busqueda}&p=${pagina}&registrosPorPagina=5">5</a>
				<a name="registrosPorPagina" value="10" type="submit" href="home?busqueda=${busqueda}&p=${pagina}&registrosPorPagina=10">10</a>
				</div>
		</div>
<%-- 		<c:out value="${VO.getProductos().size()}" /> --%>

		<!-- ... Pagination -->
		<ul style="width: 100%;"
			class="pagination pagination-lg justify-content-center">
			<c:forEach items="${paginas}" var="pagina">
				<li class="page-item ${paginaActual == pagina ? 'disabled' : ''}"><a
					class="page-link" href="home?busqueda=${busqueda}&p=${pagina}&registrosPorPagina=${registrosPorPagina}"
					tabindex="-1">${pagina}</a></li>
			</c:forEach>
		</ul>
		<!-- home?busqueda=${busqueda}&p=${pagina} -->

		<!-- Inicio Tabla -->
		<table border="1" class="table table-hover">
			<thead class="thead-dark">
				<tr>
					<th scope="col">Código</th>
					<th scope="col">Nombre</th>
					<th scope="col">Precio</th>
					<th scope="col">Stock</th>
					<th scope="col">Acciones</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${VO.getProductos()}" var="u">
					<tr>
						<td>${u.getIdProducto()}</td>
						<td>${u.getNombre()}</td>
						<td>${u.getPrecio()}</td>
						<td>${u.getStock()}</td>
						<td><a href="editarForm?idProducto=${u.getIdProducto()}"
							class="btn btn-primary btn-sm">Editar</a> <a
							href="eliminar?idProducto=${u.getIdProducto()}"
							class="btn btn-danger btn-sm">Eliminar</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<!-- Fin tabla -->
	</div>
	<!-- Fin Contenido -->

		<script>
		
		function clearInput(){
		var test = document.getElementById('search').value = '';
		console.log("test: "+ test);	
		}
		
		</script>

</body>
</html>