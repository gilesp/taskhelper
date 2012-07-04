package uk.co.vurt.hakken.domain.job;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Submission implements Serializable {

	private static final long serialVersionUID = 2876749616083811529L;
	
	private Long id;
	private String username;
	private Long jobId;
	private String taskDefinitionName;
	private List<DataItem> dataItems;
	
	public Submission(){
		dataItems = new ArrayList<DataItem>();
	}
	
	public Submission(String username, long jobId, List<DataItem> dataItems) {
		this();
		this.username = username;
		this.jobId = jobId;
		this.dataItems = dataItems;
	}

	public Submission(Long id, String username, long jobId, List<DataItem> dataItems){
		this(username, jobId, dataItems);
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTaskDefinitionName() {
		return taskDefinitionName;
	}

	public void setTaskDefinitionName(String taskDefinitionName) {
		this.taskDefinitionName = taskDefinitionName;
	}

	@Override
	public String toString() {
		return "Submission [username=" + username + ", jobId=" + jobId
				+ ", dataItems=" + dataItems + "]";
	}
	
	
}
