package model.dao;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import model.ModelException;
import model.Customer;

public class MySqlCustomerDAO implements CustomerDao {

    public boolean save(Customer customer) throws ModelException {
        DBHandler db = new DBHandler();
        String sqlInsert = "INSERT INTO customer (name, telephone, email, vehicle, plate) VALUES (?, ?, ?, ?, ?);";
        //"INSERT INTO customers VALUES " + "(DEFAULT, ?, ?, ?, ?, ?);";
        
        try {
            db.prepareStatement(sqlInsert);
            db.setString(1, customer.getName());
            db.setString(2, customer.getTelephone());
            db.setString(3, customer.getEmail());
            db.setString(4, customer.getVehicle());
            db.setString(5, customer.getPlate());
            
            // Debugging: Print the SQL statement and the parameters
            System.out.println("Executing SQL: " + sqlInsert);
            System.out.println("Parameters: " + customer.getName() + ", " + customer.getTelephone() + ", " + customer.getEmail() + ", " + customer.getVehicle() + ", " + customer.getPlate());

            return db.executeUpdate() > 0;
        } catch (Exception e) {
            throw new ModelException("Erro ao salvar cliente: " + e.getMessage(), e);
        }
    }

    public boolean update(Customer customer) throws ModelException {
        DBHandler db = new DBHandler();
        String sqlUpdate = "UPDATE customer SET name = ?, telephone = ?, email = ?, vehicle = ?, plate = ? WHERE id = ?;";
        
        try {
            db.prepareStatement(sqlUpdate);
            db.setString(1, customer.getName());
            db.setString(2, customer.getTelephone());
            db.setString(3, customer.getEmail());
            db.setString(4, customer.getVehicle());
            db.setString(5, customer.getPlate());
            db.setInt(6, customer.getId());

            // Debugging: Print the SQL statement and the parameters
            System.out.println("Executing SQL: " + sqlUpdate);
            System.out.println("Parameters: " + customer.getName() + ", " + customer.getTelephone() + ", " + customer.getEmail() + ", " + customer.getVehicle() + ", " + customer.getPlate() + ", " + customer.getId());
            
            return db.executeUpdate() > 0;
        } catch (Exception e) {
            throw new ModelException("Erro ao atualizar cliente: " + e.getMessage(), e);
        }
    }

    public boolean delete(Customer customer) throws ModelException {
        DBHandler db = new DBHandler();
        String sqlDelete = "DELETE FROM customer WHERE id = ?;";
        
        try {
            db.prepareStatement(sqlDelete);
            db.setInt(1, customer.getId());
            return db.executeUpdate() > 0;
        } catch (ModelException e) {
            if (e.getCause() instanceof SQLIntegrityConstraintViolationException) {
                return false;
            }
            throw e;
        }
    }

    public List<Customer> listAll() throws ModelException {
        DBHandler db = new DBHandler();
        List<Customer> customers = new ArrayList<>();
        String sqlQuery = "SELECT * FROM customer;";
        
        try {
            db.createStatement();
            db.executeQuery(sqlQuery);
            while (db.next()) {
                Customer u = createCustomer(db);
                customers.add(u);
            }
            return customers;
        } catch (Exception e) {
            throw new ModelException("Erro ao listar clientes: " + e.getMessage(), e);
        }
    }

    public Customer findById(int id) throws ModelException {
        DBHandler db = new DBHandler();
        String sql = "SELECT * FROM customer WHERE id = ?;";
        
        try {
            db.prepareStatement(sql);
            db.setInt(1, id);
            db.executeQuery();
            Customer u = null;
            while (db.next()) {
                u = createCustomer(db);
                break;
            }
            return u;
        } catch (Exception e) {
            throw new ModelException("Erro ao buscar cliente por ID: " + e.getMessage(), e);
        }
    }

    private Customer createCustomer(DBHandler db) throws ModelException {
        try {
            Customer u = new Customer(db.getInt("id"));
            u.setName(db.getString("name"));
            u.setTelephone(db.getString("telephone"));
            u.setEmail(db.getString("email"));
            u.setVehicle(db.getString("vehicle"));
            u.setPlate(db.getString("plate"));
            return u;
        } catch (Exception e) {
            throw new ModelException("Erro ao criar objeto Customer: " + e.getMessage(), e);
        }
    }
}
