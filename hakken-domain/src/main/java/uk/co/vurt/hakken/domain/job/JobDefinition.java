package uk.co.vurt.hakken.domain.job;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import uk.co.vurt.hakken.domain.task.TaskDefinition;

public class JobDefinition implements Serializable{
	
	private Long id;
	private String remoteId;
	private String name;
	private Long taskDefinitionId;
	private Date created;
	private Date due;
	private String status;
	private boolean adhoc = false;
	private String group;
	private String notes;
	private Set<DataItem> dataItems = new HashSet<DataItem>();
	private boolean modified;
	private String serverError;
	
	public JobDefinition(){}
	
	public JobDefinition(Long id, String remoteId, String name, Long taskDefinitionId, Date created,
			Date due, String status, String notes) {
		super();
		this.id = id;
		this.remoteId = remoteId;
		this.name = name;
		this.taskDefinitionId = taskDefinitionId;
		this.created = created;
		this.due = due;
		this.status = status;
		this.notes = notes;
	}

	public JobDefinition(Long id, String remoteId, String name, Long taskDefinitionId,
			Date created, Date due, String status, String group, String notes,
			Set<DataItem> dataItems, boolean modified) {
        this(id, remoteId, name, taskDefinitionId, created, due, status, notes);
		this.created = created;
		this.due = due;
		this.status = status;
		this.group = group;
		this.dataItems = dataItems;
		this.modified = modified;
	}
	
	public JobDefinition(Long id, String remoteId, String name, Long taskDefinitionId,
			Date created, Date due, String status, boolean adhoc, String group,
			String notes, Set<DataItem> dataItems, boolean modified,
			String serverError) {
		this(id, remoteId, name, taskDefinitionId, created, due, status, group, notes, dataItems, modified);
		this.adhoc = adhoc;
		this.serverError = serverError;
	}

	public String getStatus() {
		return status;
	}

	public boolean isModified() {
		return modified;
	}

	public void setModified(boolean modified) {
		this.modified = modified;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getId() {
		return id;
	}
	
	public String getRemoteId() {
	    return remoteId;
	}

	public String getName() {
		return name;
	}

	public Date getCreated() {
		return created;
	}

	public Date getDue() {
		return due;
	}

	public String getGroup() {
		return group;
	}
	
	public String getNotes() {
		return notes;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public void setRemoteId(String remoteId) {
	    this.remoteId = remoteId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public void setDue(Date due) {
		this.due = due;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Set<DataItem> getDataItems() {
		return dataItems;
	}

	public boolean isAdhoc() {
		return adhoc;
	}

	public void setAdhoc(boolean adhoc) {
		this.adhoc = adhoc;
	}

	public void setDataItems(Set<DataItem> dataItems) {
		this.dataItems = dataItems;
	}

	public String getServerError() {
		return serverError;
	}

	public void setServerError(String serverError) {
		this.serverError = serverError;
	}

	@Override
	public String toString() {
		return "JobDefinition [id=" + id + ", name=" + name + ", definition_id="
				+ taskDefinitionId + ", created=" + created + ", due=" + due
				+ ", status=" + status + "]";
	}
	
	public Long getTaskDefinitionId() {
		return taskDefinitionId;
	}

	public void setTaskDefinitionId(Long taskDefinitionId) {
		this.taskDefinitionId = taskDefinitionId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj){
			return true;
		}
		if (obj == null){
			return false;
		}
		if (getClass() != obj.getClass()){
			return false;
		}
		JobDefinition other = (JobDefinition) obj;
		if (id != other.id){
			return false;
		}
		return true;
	}
}
