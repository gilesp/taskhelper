package uk.co.vurt.hakken.security.auth;

public class AllowAlwaysAuthenticator implements Authenticator {

	public boolean authenticate(String username, String password) {
		return true;
	}

	public String getErrorMessage() {
		// TODO Auto-generated method stub
		return null;
	}

}
