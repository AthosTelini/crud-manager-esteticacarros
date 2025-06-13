package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Credential;
import model.ModelException;
import model.dao.CredentialDAO;
import model.dao.DAOFactory;
import model.utils.PasswordEncryptor;


@WebServlet(urlPatterns = {"/login", "/logout"})
public class LoginController extends HttpServlet{

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {

		String userLogin = req.getParameter("user_login");
		String userPW = req.getParameter("user_pw");

		CredentialDAO dao = DAOFactory.createDAO(CredentialDAO.class);
		Credential credential = null;

		try {
			// Busca o usuário 'admin' no banco
			credential = dao.findByUsername(userLogin);
		} catch (ModelException e) {
			e.printStackTrace();
		}

		// Verifica se o usuário é 'admin' e se a senha está correta
		if (credential != null && "admin".equals(credential.getUsername()) && PasswordEncryptor.checkPassword(userPW, credential.getPassword())) {

			// Se a autenticação for bem-sucedida, cria a sessão
			req.getSession().setAttribute("usuario_logado", credential.getUsername());
			resp.sendRedirect(req.getContextPath() + "/posts");
		} else {
			// Se falhar, redireciona para a página de login com um parâmetro de erro
			resp.sendRedirect(req.getContextPath() + "/login.jsp?erro=true");
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		// Invalida a sessão para fazer o logout
		HttpSession session = req.getSession(false);
		
		if (session != null) {
			session.invalidate();
        }
		
		// Redireciona para a página de login
		resp.sendRedirect(req.getContextPath() + "/login.jsp");
	}
}