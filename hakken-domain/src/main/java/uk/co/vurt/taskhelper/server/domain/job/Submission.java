package uk.co.vurt.taskhelper.server.domain.job;

import java.util.Set;

public class Submission {
	

	private String username;
	
	private int jobId;
	
	private Set<DataItem> dataitems;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getJobId() {
		return jobId;
	}

	public void setJobId(int jobId) {
		this.jobId = jobId;
	}

	public Set<DataItem> getDataitems() {
		return dataitems;
	}

	public void setDataitems(Set<DataItem> dataitems) {
		this.dataitems = dataitems;
	}
	
	
}
