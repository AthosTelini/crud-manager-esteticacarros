<%-- Em /webapp/form-company.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- Adicione esta taglib para formatar as datas --%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <%@include file="base-head.jsp"%>
    <%-- Título dinâmico, como você já fez --%>
    <title>${action == 'insert' ? 'Nova Empresa' : 'Editar Empresa'}</title> 
</head>
<body>
    <%@include file="nav-menu.jsp"%>
    
    <div id="container" class="container-fluid">
        <h3 class="page-header">${action == 'insert' ? 'Adicionar Empresa' : 'Editar Empresa'}</h3>
        
        <form action="${pageContext.request.contextPath}/company/${action}" method="POST">
            
            <%-- 1. ADICIONAR: Campo oculto com o ID da empresa para o update --%>
            <c:if test="${action == 'update'}">
                <input type="hidden" name="id" value="${company.id}">
            </c:if>
            
            <div class="row">
                <div class="form-group col-md-6">
                    <label for="name">Nome</label>
                    <%-- 2. ALTERAR: Adicionar value para preencher o nome --%>
                    <input type="text" class="form-control" id="name" name="name" 
                           value="${company.name}" autofocus="autofocus" placeholder="Nome Empresa" 
                           required oninvalid="this.setCustomValidity('Por favor, informe o nome da empresa.')"
                           oninput="setCustomValidity('')" />
                </div>
                
                <div class="form-group col-md-6">
                    <label for="role">Cargo</label>
                    <%-- 3. ALTERAR: Adicionar value para preencher o cargo --%>
                    <input type="text" class="form-control" id="role" name="role" 
                           value="${company.role}" placeholder="Cargo ocupado" 
                           required oninvalid="this.setCustomValidity('Por favor, informe o cargo.')"
                           oninput="setCustomValidity('')" />
                </div>              
            </div>
            
            <div class="row">
                <div class="form-group col-md-4">
                    <label for="start">Data início</label>
                    <%-- 4. ALTERAR: Adicionar value com formatação de data --%>
                    <input type="date" class="form-control" id="start" name="start" 
                           value="<fmt:formatDate value='${company.start}' pattern='yyyy-MM-dd'/>"
                           required oninvalid="this.setCustomValidity('Por favor, informe a data de início.')"
                           oninput="setCustomValidity('')" />
                </div>
                
                <div class="form-group col-md-4">
                    <label for="end">Data saída</label>
                     <%-- 5. ALTERAR: Adicionar value com formatação de data --%>
                    <input type="date" class="form-control" id="end" name="end" 
                           value="<fmt:formatDate value='${company.end}' pattern='yyyy-MM-dd'/>"
                           placeholder="Data de saída"             
                           oninvalid="this.setCustomValidity('Por favor, informe a data de saída')"
                           oninput="setCustomValidity('')" />
                </div>
                
                <div class="form-group col-md-4">
                    <label for="user">Usuário</label>
                    <select id="user" class="form-control selectpicker" name="user" 
                            required oninvalid="this.setCustomValidity('Por favor, informe o usuário.')"
                            oninput="setCustomValidity('')">
                      <option value="">Selecione um usuário</option>
                      <c:forEach var="user" items="${users}">
                        <%-- 6. ALTERAR: Lógica para selecionar o usuário correto --%>
                        <option value="${user.id}" ${user.id == company.user.id ? 'selected' : ''}>
                            ${user.name}
                        </option>   
                      </c:forEach>
                    </select>
                </div>
            </div>
            
            <hr />
            <div id="actions" class="row pull-right">
                <div class="col-md-12">
                    <a href="${pageContext.request.contextPath}/companies" class="btn btn-default">Cancelar</a>
                    <%-- 7. ALTERAR: Botão dinâmico --%>
                    <button type="submit" class="btn btn-primary">${action == 'insert' ? 'Cadastrar' : 'Salvar'}</button>
                </div>
            </div>
        </form>
    </div>
</body>
</html>