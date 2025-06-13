package controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelException;
import model.Customer;
import model.dao.DAOFactory;
import model.dao.UserDAO;
import model.dao.CustomerDao;

import java.sql.SQLIntegrityConstraintViolationException;

@WebServlet(urlPatterns = {"/customers", "/customer/form", "/customer/delete", "/customer/insert", "/customer/update"})
public class CustomerController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getRequestURI();
		
		switch (action) {
		case "/crud-manager/customer/form": {
			listCustomers(req);
			req.setAttribute("action", "insert");
			ControllerUtil.forward(req, resp, "/form-Cliente.jsp");
			break;
		}
		case "/crud-manager/customer/update": {
			listCustomers(req);
			Customer customer = loadCustomer(req);
			req.setAttribute("customer", customer);
			req.setAttribute("action", "update");
			ControllerUtil.forward(req, resp, "/form-Cliente.jsp");
			break;
		}
		default:
			listCustomers(req);
			
			ControllerUtil.transferSessionMessagesToRequest(req);
		
			ControllerUtil.forward(req, resp, "/customer.jsp");
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getRequestURI();
		
		if (action == null || action.equals("") ) {
			ControllerUtil.forward(req, resp, "/index.jsp");
			return;
		}
		
		switch (action) {
		case "/crud-manager/customer/delete":
			deleteCustomer(req, resp);
			break;	
		case "/crud-manager/customer/insert": {
			insertCustomer(req, resp);
			break;
		}
		case "/crud-manager/customer/update": {
			updateCustomer(req, resp);
			break;
		}
		default:
			System.out.println("URL inválida " + action);
			break;
		}
			
		ControllerUtil.redirect(resp, req.getContextPath() + "/customers");
	}

	private Customer loadCustomer(HttpServletRequest req) {
		String customerIdParameter = req.getParameter("customerId");
		
		int customerId = Integer.parseInt(customerIdParameter);
		
		CustomerDao dao = DAOFactory.createDAO(CustomerDao.class);
		
		try {
			Customer customer = dao.findById(customerId);
			
			if (customer == null)
				throw new ModelException("Cliente não encontrado para alteração");
			
			return customer;
		} catch (ModelException e) {
			// log no servidor
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
		
		return null;
	}
	
	private void listCustomers(HttpServletRequest req) {
		CustomerDao dao = DAOFactory.createDAO(CustomerDao.class);
		
		List<Customer> customers = null;
		try {
			customers = dao.listAll();
		} catch (ModelException e) {
			// Log no servidor
			e.printStackTrace();
		}
		
		if (customers != null)
			req.setAttribute("customers", customers);
	}
	
	private void insertCustomer(HttpServletRequest req, HttpServletResponse resp) {
		String customerName = req.getParameter("name");
		String customerTelephone = req.getParameter("telephone");
		String customerEMail = req.getParameter("mail");
		String customerVehicle = req.getParameter("vehicle");
		String customerPlate = req.getParameter("plate");
		
		Customer customer = new Customer();
		customer.setName(customerName);
		customer.setTelephone(customerTelephone);
		customer.setEmail(customerEMail);
		customer.setVehicle(customerVehicle);
		customer.setPlate(customerPlate); 
		
		CustomerDao dao = DAOFactory.createDAO(CustomerDao.class);
		
		try {
			if (dao.save(customer)) {
				ControllerUtil.sucessMessage(req, "Cliente '" + customer.getName() + "' salvo com sucesso.");
			}
			else {
				ControllerUtil.errorMessage(req, "Cliente '" + customer.getName() + "' não pode ser salvo.");
			}
				
		} catch (ModelException e) {
			// log no servidor
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}
		

	private void updateCustomer(HttpServletRequest req, HttpServletResponse resp) {
		String customerName = req.getParameter("name");
		String customerTelephone = req.getParameter("telephone");
		String customerEMail = req.getParameter("mail");
		String customerVehicle = req.getParameter("vehicle");
		String customerPlate = req.getParameter("plate"); 
		
		Customer customer = loadCustomer(req);
		customer.setName(customerName);
		customer.setTelephone(customerTelephone);
		customer.setEmail(customerEMail);
		customer.setVehicle(customerVehicle);
		customer.setPlate(customerPlate); 
		
		CustomerDao dao = DAOFactory.createDAO(CustomerDao.class);
	    
	    try {
	        if (dao.update(customer)) {
	            ControllerUtil.sucessMessage(req, "Cliente '" + customer.getName() + "' atualizado com sucesso.");
	        }
	        else {
	            ControllerUtil.errorMessage(req, "Cliente '" + customer.getName() + "' não pode ser atualizado.");
	        }
	    } catch (ModelException e) {
	        // log no servidor
	        e.printStackTrace();
	        ControllerUtil.errorMessage(req, e.getMessage());
	    }
	}
	
	private void deleteCustomer(HttpServletRequest req, HttpServletResponse resp) {
		String customerIdParameter = req.getParameter("id");
		
		int customerId = Integer.parseInt(customerIdParameter);
		
		CustomerDao dao = DAOFactory.createDAO(CustomerDao.class);
		
		try {
			Customer customer = dao.findById(customerId);
			
			if (customer == null)
				throw new ModelException("Cliente não encontrado para deleção.");
			
			if (dao.delete(customer)) {
				ControllerUtil.sucessMessage(req, "Cliente '" + customer.getName() + "' deletado com sucesso.");
			}
			else {
				ControllerUtil.errorMessage(req, "Cliente '" + customer.getName() + "' não pode ser deletado. Há dados relacionados ao usuário.");
			}
		} catch (ModelException e) {
			// log no servidor
			if (e.getCause() instanceof SQLIntegrityConstraintViolationException) {
				ControllerUtil.errorMessage(req, e.getMessage());
			}
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}
}
