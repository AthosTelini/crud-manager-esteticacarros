package model.dao;

import java.util.ArrayList;
import java.util.List;

import model.ModelException;
import model.Post;
import model.Customer;

public class MySQLPostDAO implements PostDAO {

	@Override
	public boolean save(Post post) throws ModelException {
		
		DBHandler db = new DBHandler();
		
		String sqlInsert = "INSERT INTO posts VALUES "
				+ " (DEFAULT, ?, CURDATE(), ?);";
		
		db.prepareStatement(sqlInsert);
		db.setString(1, post.getContent());
		db.setInt(2, post.getCustomer().getId());
		  
		return db.executeUpdate() > 0;
	}

	@Override
	public boolean update(Post post) throws ModelException {
		
		DBHandler db = new DBHandler();
		
		String sqlUpdate = "UPDATE posts "
						 + " SET content = ?,"
						 + " customer_id = ?"
						 + " WHERE id = ?";
		
		db.prepareStatement(sqlUpdate);
		
		db.setString(1, post.getContent());
		db.setInt(2,post.getCustomer().getId());
		db.setInt(3, post.getId());
		
		return db.executeUpdate() > 0;
	}

	@Override
	public boolean delete(Post post) throws ModelException {
		DBHandler db = new DBHandler();
		
		String sqlDelete = " DELETE FROM posts "
		         + " WHERE id = ?;";

		db.prepareStatement(sqlDelete);		
		db.setInt(1, post.getId());
		
		return db.executeUpdate() > 0;
	}

	@Override
	public List<Post> listAll() throws ModelException {
	
		DBHandler db = new DBHandler();
		
		List<Post> posts = new ArrayList<Post>();
		
		String sqlQuery = " SELECT p.id, p.content, p.post_date, p.customer_id FROM posts p; ";
		
		db.createStatement();
	
		db.executeQuery(sqlQuery);

		while (db.next()) {
			System.out.println("Post encontrado: " + db.getString("content"));
			Post p = createPost(db);
			
			posts.add(p);
		}
		
		return posts;
	}

	@Override
	public Post findById(int id) throws ModelException {
		
		DBHandler db = new DBHandler();
				
		String sql = "SELECT * FROM posts WHERE id = ?;";
		
		db.prepareStatement(sql);
		db.setInt(1, id);
		db.executeQuery();
		
		Post p = null;
		while (db.next()) {
			p = createPost(db);
			break;
		}
		
		return p;
	}
	
	private Post createPost(DBHandler db) throws ModelException {
		Post p = new Post(db.getInt("id"));
		p.setContent(db.getString("content"));
		p.setPostDate(db.getDate("post_date"));
		
		CustomerDao customerDao = DAOFactory.createDAO(CustomerDao.class);
		
		Customer customer = customerDao.findById(db.getInt("customer_id"));
		p.setCustomer(customer);
		
		return p;
	}
}
