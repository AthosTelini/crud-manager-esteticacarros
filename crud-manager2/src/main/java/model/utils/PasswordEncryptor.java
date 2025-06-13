package model.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

public class PasswordEncryptor {

	/**
	 * Gera um hash SHA-256 da senha e o codifica em Base64.
	 * @param plainTextPassword A senha em texto plano.
	 * @return A senha criptografada.
	 */
	public static String hashPassword(String plainTextPassword) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(plainTextPassword.getBytes(StandardCharsets.UTF_8));
			return Base64.getEncoder().encodeToString(hash);
		} catch (Exception e) {
			throw new RuntimeException("Erro ao gerar hash da senha", e);
		}
	}

	/**
	 * Compara uma senha em texto plano com um hash armazenado.
	 * @param plainTextPassword A senha em texto plano digitada pelo usuário.
	 * @param hashedPassword O hash da senha armazenado no banco de dados.
	 * @return true se as senhas correspondem, false caso contrário.
	 */
	public static boolean checkPassword(String plainTextPassword, String hashedPassword) {
		if (hashedPassword == null || hashedPassword.isEmpty()) {
			return false;
        }
		String hashedPlainTextPassword = hashPassword(plainTextPassword);
		return hashedPlainTextPassword.equals(hashedPassword);
	}
}