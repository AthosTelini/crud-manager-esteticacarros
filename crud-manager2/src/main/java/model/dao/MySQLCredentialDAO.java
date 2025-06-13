package model.dao;

import model.Credential;
import model.ModelException;

public class MySQLCredentialDAO implements CredentialDAO {

    @Override
    public Credential findByUsername(String username) throws ModelException {
        DBHandler db = null;
        try {
            db = new DBHandler();
            
            String sql = "SELECT * FROM credentials WHERE username = ?;";
            
            db.prepareStatement(sql);
            db.setString(1, username);
            
            db.executeQuery();
            
            Credential c = null;
            if (db.next()) {
                c = new Credential();
                c.setId(db.getInt("id"));
                c.setUsername(db.getString("username"));
                c.setPassword(db.getString("password"));
            }
            
            return c;
        } catch (Exception e) {
            throw new ModelException("Erro ao buscar credencial por nome de usuário.", e);
        } finally {
            if (db != null) {
                // Adicione um método close() em DBHandler se ainda não existir
                // para fechar a conexão de forma segura.
                // db.close(); 
            }
        }
    }
}