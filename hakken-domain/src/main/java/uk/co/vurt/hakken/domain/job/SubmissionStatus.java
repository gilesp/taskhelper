package uk.co.vurt.hakken.domain.job;

public class SubmissionStatus {

	public enum ErrorType {
		COMMS, VALIDATION
	}

	private boolean valid = false;
	private String message;
	private ErrorType type;

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ErrorType getType() {
		return type;
	}

	public void setType(ErrorType type) {
		this.type = type;
	}

}
