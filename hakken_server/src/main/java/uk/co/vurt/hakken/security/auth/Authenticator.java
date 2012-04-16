package uk.co.vurt.hakken.security.auth;

public interface Authenticator {

	public boolean authenticate(String username, String password);
	public String getErrorMessage();
}
