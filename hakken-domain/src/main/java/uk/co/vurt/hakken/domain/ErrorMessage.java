package uk.co.vurt.hakken.domain;

public class ErrorMessage {

	String summary;
	String detail;
	
	public ErrorMessage(){}
	
	public ErrorMessage(String summary, String detail) {
		super();
		this.summary = summary;
		this.detail = detail;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}
}
