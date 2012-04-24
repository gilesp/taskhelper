package uk.co.vurt.hakken.domain.job;

import java.util.ArrayList;
import java.util.List;

public class Submission {

	private String username;
	private int jobId;
	private List<DataItem> dataItems;
	
	public Submission(){
		dataItems = new ArrayList<DataItem>();
	}
	
	public Submission(String username, int jobId, List<DataItem> dataItems) {
		super();
		this.username = username;
		this.jobId = jobId;
		this.dataItems = dataItems;
	}

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

	public List<DataItem> getDataItems() {
		return dataItems;
	}

	public void setDataItems(List<DataItem> dataItems) {
		this.dataItems = dataItems;
	}
	
	public void addDataItem(DataItem dataItem){
		this.dataItems.add(dataItem);
	}

	@Override
	public String toString() {
		return "Submission [username=" + username + ", jobId=" + jobId
				+ ", dataItems=" + dataItems + "]";
	}
	
	
}
