package controller;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Company;
import model.ModelException;
import model.User;
import model.dao.CompanyDAO;
import model.dao.DAOFactory;

@WebServlet(urlPatterns = {"/companies", "/company/form", 
		"/company/insert", "/company/delete","/company/update"})
public class CompaniesController extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		String action = req.getRequestURI();
		
		switch (action) {
		case "/crud-manager/company/form": {
			CommonsController.listUsers(req);
			//CommonsController.listCustomers(req);
			req.setAttribute("action", "insert");
			ControllerUtil.forward(req, resp, "/form-Company.jsp");			
			break;
		}
        case "/crud-manager/company/update": {
            // Carrega os dados do usuário para preencher o <select>
            CommonsController.listUsers(req);

            // Carrega a empresa que será editada
            Company company = loadCompany(req);
            if (company != null) {
                req.setAttribute("company", company);
                req.setAttribute("action", "update");
                ControllerUtil.forward(req, resp, "/form-Company.jsp"); // Reutilizaremos o mesmo formulário
            } else {
                // Se não encontrar a empresa, redireciona para a lista com uma mensagem
                ControllerUtil.errorMessage(req, "Empresa não encontrada para alteração.");
                ControllerUtil.redirect(resp, req.getContextPath() + "/companies");
            }
            break;
        }
		default:
			listCompanies(req);
			
			ControllerUtil.transferSessionMessagesToRequest(req);
		
			ControllerUtil.forward(req, resp, "/companies.jsp");
		}
	}
	
	private Company loadCompany(HttpServletRequest req) {
        String companyIdParameter = req.getParameter("companyId");
        
        if (companyIdParameter == null || companyIdParameter.isEmpty()) {
            ControllerUtil.errorMessage(req, "ID da empresa não informado.");
            return null;
        }

        int companyId = Integer.parseInt(companyIdParameter);
        CompanyDAO dao = DAOFactory.createDAO(CompanyDAO.class);
        
        try {
            Company company = dao.findById(companyId);
            
            if (company == null) {
                throw new ModelException("Empresa não encontrada para alteração.");
            }
            return company;
        } catch (ModelException e) {
            e.printStackTrace();
            ControllerUtil.errorMessage(req, e.getMessage());
            return null;
        }
    }

	private void listCompanies(HttpServletRequest req) {
		CompanyDAO dao = DAOFactory.createDAO(CompanyDAO.class);
		
		List<Company> companies = null;
		try {
			companies = dao.listAll();
		} catch (ModelException e) {
			// Log no servidor
			e.printStackTrace();
		}
		
		if (companies != null)
			req.setAttribute("companies", companies);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		String action = req.getRequestURI();
		
		switch (action) {
		case "/crud-manager/company/insert": {
			insertCompany(req, resp);			
			break;
		}
		case "/crud-manager/company/delete" :{
			
			deleteCompany(req, resp);
			
			break;
		}
		case "/crud-manager/company/update" :{
			
			updateCompany(req, resp);
			
			break;
		}
		default:
			System.out.println("URL inválida " + action);
		}
		
		ControllerUtil.redirect(resp, req.getContextPath() + "/companies");
	}

	private void updateCompany(HttpServletRequest req, HttpServletResponse resp) {
	    // Obter o ID da empresa do campo oculto do formulário
	    Integer companyId = Integer.parseInt(req.getParameter("id"));
	    
	    // Obter os outros parâmetros
	    String companyName = req.getParameter("name");
	    String role = req.getParameter("role");
	    String start = req.getParameter("start");
	    String end = req.getParameter("end");
	    Integer userId = Integer.parseInt(req.getParameter("user"));

	    // Criar o objeto Company com os dados atualizados
	    Company comp = new Company(companyId); // Usa o construtor que define o ID
	    comp.setName(companyName); //
	    comp.setRole(role); //
	    comp.setStart(ControllerUtil.formatDate(start)); //
	    comp.setEnd(ControllerUtil.formatDate(end)); //
	    comp.setUser(new User(userId)); //
	    
	    CompanyDAO dao = DAOFactory.createDAO(CompanyDAO.class); //

	    try {
	        if (dao.update(comp)) { //
	            ControllerUtil.sucessMessage(req, "Empresa '" + comp.getName() 
	            + "' atualizada com sucesso.");
	        }
	        else {
	            ControllerUtil.errorMessage(req, "Empresa '" + comp.getName()
	            + "' não pode ser atualizada.");
	        }
	    } catch (ModelException e) {
	        // log no servidor
	        e.printStackTrace();
	        ControllerUtil.errorMessage(req, e.getMessage());
	    }
	}

	private void deleteCompany(HttpServletRequest req, HttpServletResponse resp) {
		String companyIdParameter = req.getParameter("id");
		
		int companyId = Integer.parseInt(companyIdParameter);
		
		CompanyDAO dao = DAOFactory.createDAO(CompanyDAO.class);
		
		try {
			Company company = dao.findById(companyId);
			
			if (company == null)
				throw new ModelException("Empresa não encontrada para deleção.");
			
			if (dao.delete(company)) {
				ControllerUtil.sucessMessage(req, "Empresa '" + 
						company.getName() + "' deletada com sucesso.");
			}
			else {
				ControllerUtil.errorMessage(req, "Empresa '" + 
						company.getName() + "' não pode ser deletado. "
								+ "Há dados relacionados à empresa.");
			}
		} catch (ModelException e) {
			// log no servidor
			if (e.getCause() instanceof 
					SQLIntegrityConstraintViolationException) {
				ControllerUtil.errorMessage(req, e.getMessage());
			}
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}

	private void insertCompany(HttpServletRequest req, HttpServletResponse resp) {
		String companyName = req.getParameter("name");
		String role = req.getParameter("role");
		String start = req.getParameter("start");
		String end = req.getParameter("end");
		Integer userId = Integer.parseInt(req.getParameter("user"));
		
		Company comp = new Company();
		comp.setName(companyName);
		comp.setRole(role);
		comp.setStart(ControllerUtil.formatDate(start));
		comp.setEnd(ControllerUtil.formatDate(end));
		comp.setUser(new User(userId));
		
		CompanyDAO dao = DAOFactory.createDAO(CompanyDAO.class);
	
		try {
			if (dao.save(comp)) {
				ControllerUtil.sucessMessage(req, "Empresa '" + comp.getName() 
				+ "' salva com sucesso.");
			}
			else {
				ControllerUtil.errorMessage(req, "Empresa '" + comp.getName()
				+ "' não pode ser salva.");
			}
		} catch (ModelException e) {
			// log no servidor
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}
	
}
