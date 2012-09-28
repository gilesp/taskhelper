package uk.co.vurt.hakken.security.model;

public class LoginResponse {

	boolean success = false;
	String reason;
	String token;
	
	public LoginResponse(){
		super();
	}
	
	
	public LoginResponse(boolean success, String reason, String token) {
		this();
		this.success = success;
		this.reason = reason;
		this.token = token;
	}

	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	
}
