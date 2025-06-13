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
			<h3 class="page-header">Adicionar Cliente</h3>

			<form action="${pageContext.request.contextPath}/customer/${action}" method="POST">
				<input type="hidden" value="${customer.getId()}" name="customerId">
				<div class="row">
					
					<div class="form-group col-md-4">
			            <label for="name">Nome</label>
			            <input type="text" class="form-control" id="name" name="name" 
			                   autofocus="autofocus" placeholder="Nome do cliente" 
			                   required oninvalid="this.setCustomValidity('Por favor, informe o nome do cliente.')"
			                   oninput="setCustomValidity('')"
			                   value="${customer.getName()}">
			        </div>
			        <div class="form-group col-md-4">
			            <label for="telephone">Telefone</label>
			            <input type="text" class="form-control" id="telephone" name="telephone" 
			                   placeholder="Telefone do cliente" 
			                   required oninvalid="this.setCustomValidity('Por favor, informe o telefone do cliente.')"
			                   oninput="setCustomValidity('')"
			                   value="${customer.getTelephone()}">
			        </div>
			        <div class="form-group col-md-4">
			            <label for="email">E-mail</label>
			            <input type="email" class="form-control" id="mail" name="mail" 
			                   placeholder="E-mail do cliente" 
			                   required oninvalid="this.setCustomValidity('Por favor, informe o e-mail do cliente.')"
			                   oninput="setCustomValidity('')"
			                   value="${customer.getEmail()}">
			        </div>
			        <div class="form-group col-md-4">
			            <label for="vehicle">Veículo</label>
			            <input type="text" class="form-control" id="vehicle" name="vehicle" 
			                   placeholder="Veículo do cliente" 
			                   required oninvalid="this.setCustomValidity('Por favor, informe o veículo do cliente.')"
			                   oninput="setCustomValidity('')"
			                   value="${customer.getVehicle()}">
			        </div>
			        <div class="form-group col-md-4">
			            <label for="plate">Placa</label>
			            <input type="text" class="form-control" id="plate" name="plate" 
			                   placeholder="Placa do veículo" 
			                   required oninvalid="this.setCustomValidity('Por favor, informe a placa do veículo.')"
			                   oninput="setCustomValidity('')"
			                   value="${customer.getPlate()}">
			        </div>
			    </div>
			    <hr />
			    <div id="actions" class="row pull-right">
			        <div class="col-md-12">
			            <a href="${pageContext.request.contextPath}/customers" class="btn btn-default">Cancelar</a>
			            <button type="submit" class="btn btn-primary">${not empty customer ? "Alterar Cliente" : "Cadastrar Cliente"}</button>
			        </div>
				</div>
			</form>
		</div>

		<script src="js/jquery.min.js"></script>
		<script src="js/bootstrap.min.js"></script>
	</body>
</html>
