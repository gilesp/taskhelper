package uk.co.vurt.hakken.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import uk.co.vurt.hakken.security.HashUtils;
import uk.co.vurt.hakken.security.auth.Authenticator;
import uk.co.vurt.hakken.security.model.LoginResponse;

@Controller
@RequestMapping("/auth")
public class AuthenticationController extends RESTController{

	@Autowired
	private Authenticator authenticator;

	/**
	 * This method should only ever be accessed over SSL!
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody
	LoginResponse authenticate(
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = true) String password) {
		LoginResponse response = new LoginResponse();
		if (username != null && password != null) {
			// Perform LDAP lookup using username & password to bind.
			// if authentication is successful, retrieve the user's shared
			// secret from the database and send that to them as the response.
			response.setSuccess(authenticator.authenticate(username, password));
			if (response.isSuccess()) {
				// TODO: Retrieve shared secret
				response.setToken(HashUtils.SHARED_SECRET);
			} else {
				response.setReason(authenticator.getErrorMessage());
			}
		}
		return response;
	}

	public void setAuthenticator(Authenticator authenticator) {
		this.authenticator = authenticator;
	}

}
