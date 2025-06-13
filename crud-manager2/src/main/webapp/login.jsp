<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="pt-br">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Login - CRUD Manager</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-theme.min.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" />
  </head>
  <body>
    <div class="container" style="display: flex; justify-content: center; align-items: center; height: 100vh;">
      <div class="card shadow-sm p-4" style="width: 100%; max-width: 400px; padding: 20px; border: 1px solid #ddd; border-radius: 5px; background-color: #f9f9f9;">
        <h2 class="text-center" style="margin-bottom: 20px;">CRUD Manager</h2>
        
        <form action="${pageContext.request.contextPath}/login" method="POST">
          <div class="form-group">
            <label for="user_login_id">Usuário</label>
            <input type="text" class="form-control" id="user_login_id" name="user_login" required />
          </div>
          
          <div class="form-group">
            <label for="user_pw_id">Senha</label>
            <input type="password" class="form-control" id="user_pw_id" name="user_pw" required />
          </div>
          
          <div class="form-group">
            <button type="submit" class="btn btn-primary btn-block">Entrar</button>
          </div>
          

        </form>
      </div>
    </div>
    <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>


    <c:if test="${param.erro == 'true'}">
        <script type="text/javascript">
            alert("Usuário ou senha inválidos.");
        </script>
    </c:if>

  </body>
</html>