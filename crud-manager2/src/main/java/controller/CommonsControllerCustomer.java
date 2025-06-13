package controller;

import java.util.List;
import jakarta.servlet.http.HttpServletRequest;
import model.ModelException;
import model.dao.DAOFactory;
import model.Customer;
import model.dao.CustomerDao;

public class CommonsControllerCustomer {
	public static void listCustomers(HttpServletRequest req) {
	CustomerDao dao = DAOFactory.createDAO(CustomerDao.class);
		
	List<Customer> listCustomers = null;
		try {
			listCustomers = dao.listAll();
		} catch (ModelException e) {
			// Log no servidor
			e.printStackTrace();
		}
		
		if (listCustomers != null)
			req.setAttribute("customer", listCustomers);		
	}
}
