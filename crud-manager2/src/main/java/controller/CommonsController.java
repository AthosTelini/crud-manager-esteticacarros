package controller;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import model.ModelException;
import model.User;
import model.Customer;
import model.dao.DAOFactory;
import model.dao.UserDAO;
import model.dao.CustomerDao;

public class CommonsController {
    
    // Método para listar usuários
    public static void listUsers(HttpServletRequest req) {
        UserDAO dao = DAOFactory.createDAO(UserDAO.class);
        
        List<User> listUsers = null;
        try {
            listUsers = dao.listAll();
        } catch (ModelException e) {
            // Log no servidor
            e.printStackTrace();
        }
        
        if (listUsers != null)
            req.setAttribute("users", listUsers);        
    }
    
    // Método para listar clientes
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
            req.setAttribute("customers", listCustomers);        
    }
}
