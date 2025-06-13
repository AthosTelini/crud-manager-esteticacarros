package model.dao;

import java.util.List;

import model.ModelException;
import model.Customer;

public interface CustomerDao {
	boolean save(Customer customer) throws ModelException ;
	boolean update(Customer customer) throws ModelException;
	boolean delete(Customer customer) throws ModelException;
	List<Customer> listAll() throws ModelException;
	Customer findById(int id) throws ModelException;
}
