<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="pt-br">
	<head>
		<%@include file="base-head.jsp"%>
	</head>
	<body>
		<%@include file="nav-menu.jsp"%>
			
		<div id="container" class="container-fluid">
			<h3 class="page-header">Adicionar Serviços</h3>

			<form action="${pageContext.request.contextPath}/post/${action}" method="POST">
				<input type="hidden" value="${post.getId()}" name="postId">
				<div class="row">
					<div class="form-group col-md-6">
						<label for="content">Serviços</label>
						<input type="text" class="form-control" id="content" name="content" 
							   autofocus="autofocus" placeholder="Serviço prestado" 
							   required oninvalid="this.setCustomValidity('Por favor, informe o Serviço que foi feito.')"
							   oninput="setCustomValidity('')"
							   value="${post.getContent()}">
					</div>

					<div class="form-group col-md-6">
						<label for="customer">Cliente</label>
						<select id="customer" class="form-control selectpicker" name="customer" 
							    required oninvalid="this.setCustomValidity('Por favor, informe o Cliente')"
							    oninput="setCustomValidity('')">
						  <option value="" disabled ${not empty post ? "" : "selected"}>Selecione um cliente</option>
						  <c:forEach var="customer" items="${customers}">
						  	<option value="${customer.getId()}" ${post.getCustomer() != null && post.getCustomer().getId() == customer.getId() ? "selected" : ""}>
						  		${customer.getName()}
						  	</option>	
						  </c:forEach>
						</select>
					</div>
				</div>
				<hr />
				<div id="actions" class="row pull-right">
					<div class="col-md-12">
						<a href="${pageContext.request.contextPath}/posts" class="btn btn-default">Cancelar</a>
						<button type="submit" class="btn btn-primary">${not empty post ? "Alterar Serviço" : "Criar Serviço"}</button>
					</div>
				</div>
			</form>
		</div>

		<script src="js/jquery.min.js"></script>
		<script src="js/bootstrap.min.js"></script>
	</body>
</html>
