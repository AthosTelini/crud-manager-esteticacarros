<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:directive.page contentType="text/html; charset=UTF-8" />

<nav id="menu" class="navbar navbar-inverse navbar-fixed-top">
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
		    <span class="sr-only">Toggle navigation</span>
		    <span class="icon-bar"></span>
		    <span class="icon-bar"></span>
		    <span class="icon-bar"></span>
		   </button>
			<a class="navbar-brand" href="${pageContext.request.contextPath}/posts">
		    	<span><img height="30px" width="30px" alt="PM" src="${pageContext.request.contextPath}/images/logo.png"><strong>&nbsp;CRUD Manager | Sistema de estética automotiva</strong></span>
		    </a>
		</div>
		<div id="navbar" class="navbar-collapse collapse">
			<ul class="nav navbar-nav navbar-right">
				<li><a href="${pageContext.request.contextPath}/posts"><span class="glyphicon glyphicon-home" /><strong>&nbsp;Início</strong></a></li>
				<li><a href="${pageContext.request.contextPath}/customers"><span class="glyphicon glyphicon-user" /><strong>&nbsp;Cliente</strong></a></li>
				<li><a href="${pageContext.request.contextPath}/users"><span class="glyphicon glyphicon-user" /><strong>&nbsp;Usuario</strong></a></li>
				<li><a href="${pageContext.request.contextPath}/posts"><span class="glyphicon glyphicon-pencil" /><strong>&nbsp;Serviços</strong></a></li>
				<li><a href="${pageContext.request.contextPath}/companies"><span class="glyphicon glyphicon-pushpin" /><strong>&nbsp;Empresa</strong></a></li>
				
				<c:if test="${sessionScope.usuario_logado != null}">
					<li class="dropdown">
						<a class="dropdown-toggle" data-toggle="dropdown" href="#">
							<strong><span class="glyphicon glyphicon-user"></span>&nbsp;${sessionScope.usuario_logado}</strong>
							<span class="caret"></span>
						</a>
						<ul class="dropdown-menu">
							<li>
								<a href="${pageContext.request.contextPath}/logout">
									<span class="glyphicon glyphicon-log-out" /></span>&nbsp;Sair
								</a>
							</li>
						</ul>
					</li>
				</c:if>
			</ul>
		</div>
	</div>
</nav>
<br /><br /><br />