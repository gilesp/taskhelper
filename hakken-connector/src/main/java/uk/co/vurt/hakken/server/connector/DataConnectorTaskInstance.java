package uk.co.vurt.hakken.server.connector;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Is this class needed? Could the DataConnector simply return Job instances instead?
 * @author giles.paterson
 *
 */
public class DataConnectorTaskInstance {

	private String taskDefinitionName;
	
	private Date createdDate;
	private Date dueDate;
	private Long id;
	private String label;
	private String description;
	
	//Will use these for geographic tagging of instances, at some future point
//	private Long xCoord;
//	private Long yCoord;
	
	private Map<String, Object> initialDataMap = new HashMap<String, Object>();

	public String getTaskDefinitionName() {
		return taskDefinitionName;
	}

	public void setTaskDefinitionName(String taskDefinitionName) {
		this.taskDefinitionName = taskDefinitionName;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Map<String, Object> getInitialDataMap() {
		return initialDataMap;
	}

	public void setInitialDataMap(Map<String, Object> initialDataMap) {
		this.initialDataMap = initialDataMap;
	}

	
}
