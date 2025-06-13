package model.dao;

import model.Credential;
import model.ModelException;

public interface CredentialDAO {
    Credential findByUsername(String username) throws ModelException;
}