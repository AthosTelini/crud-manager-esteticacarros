package filters;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter("/*")
public class LoginFilter implements Filter {
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, 
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest httpReq = (HttpServletRequest) req;
		HttpServletResponse httpRes = (HttpServletResponse) res;
		
		// Verifica se o atributo 'usuario_logado' existe na sessão
		boolean userLogged = httpReq.getSession().getAttribute("usuario_logado") != null;
		
		String url = httpReq.getRequestURI();
		
		// Libera o acesso para a página de login e para o servlet de login
		boolean isPublicPage = url.endsWith("login.jsp") || url.endsWith("login");
		
		// Libera o acesso para recursos estáticos (CSS, JS, fontes, etc.)
		boolean isPublicRes = url.contains("/css/") || url.contains("/js/") || url.contains("/fonts/");
		
		if (userLogged || isPublicPage || isPublicRes) {
			// Se o usuário está logado ou a página é pública, permite o acesso
			chain.doFilter(req, res);
		} else {
			// Se não, redireciona para a página de login
			httpRes.sendRedirect(httpReq.getContextPath() + "/login.jsp");
		}
	}
}