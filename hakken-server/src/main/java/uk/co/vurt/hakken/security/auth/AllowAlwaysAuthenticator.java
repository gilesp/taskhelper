package uk.co.vurt.hakken.security.auth;

/**
 * Test authenticator that does not rely on any external infrastructure
 * 
 * User will always be authenticated, regardless of credentials passed.
 *
 */
public class AllowAlwaysAuthenticator implements Authenticator {

	public boolean authenticate(String username, String password) {
		return true;
	}

	public String getErrorMessage() {
		return null;
	}

}
