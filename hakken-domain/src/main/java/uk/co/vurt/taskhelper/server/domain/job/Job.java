package uk.co.vurt.taskhelper.server.domain.job;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

import uk.co.vurt.taskhelper.server.domain.definition.AutoTaskDefinition;
import uk.co.vurt.taskhelper.server.domain.definition.ManualTaskDefinition;
import uk.co.vurt.taskhelper.server.domain.user.Person;
import uk.co.vurt.taskhelper.server.util.StringCopyTransformer;
import flexjson.JSON;
import flexjson.JSONSerializer;

@RooJavaBean
@RooToString
@RooJson(toJsonMethod="jsonify",toJsonArrayMethod="jsonifyArray")
@RooJpaActiveRecord(finders = { "findJobsByPerson", "findJobsByStatus" })
public class Job {

    @NotNull
    @ManyToOne
    private Person person;

    @ManyToOne
    private ManualTaskDefinition manualTask;

    @ManyToOne
    private AutoTaskDefinition autoTask;
    
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date created;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date due;

    @Enumerated
    private Status status = Status.NEW;

    private String groupname = "Personal";
    
    @Size(max = 2000)
    private String name = "Undefined";
    
    @Size(max = 4000)
    private String notes;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy="job")
    private Set<DataItem> dataItems = new HashSet<DataItem>();
    
    public void setManualTask(ManualTaskDefinition manualTask){
    	this.manualTask = manualTask;
    	this.name = manualTask.getName();
    }
    
    public void setAutoTask(AutoTaskDefinition autoTask){
    	this.autoTask = autoTask;
    	this.name = autoTask.getDescription();
    }
    
    @JSON(include=false)
    public ManualTaskDefinition getManualTask(){
    	return manualTask;
    }
    
    @JSON(include=false)
    public AutoTaskDefinition getAutoTask(){
    	return autoTask;
    }
    
    @JSON(include=true)
    public String getTask(){
    	if(manualTask != null){
    		return manualTask.jsonify();
    	}else if(autoTask != null){
    		return autoTask.toJson();
    	}else {
    		return "";
    	}
    }
    
    public String jsonify(){
    	return new JSONSerializer().include("id", "name", "person", "created", "due", "status", "groupname", "notes", "task", "dataItems.pageName", "dataItems.name", "dataItems.type", "dataItems.value").transform(new StringCopyTransformer("task"), "task").exclude("*").serialize(this);
    }
    
    public static String jsonifyArray(Collection<Job> collection){
    	return new JSONSerializer().include("id", "name", "person", "created", "due", "status", "groupname", "notes", "task", "dataItems.pageName", "dataItems.name", "dataItems.type", "dataItems.value").transform(new StringCopyTransformer("task"), "task").exclude("*").serialize(collection);
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((autoTask == null) ? 0 : autoTask.hashCode());
		result = prime * result + ((created == null) ? 0 : created.hashCode());
		result = prime * result + ((due == null) ? 0 : due.hashCode());
		result = prime * result
				+ ((groupname == null) ? 0 : groupname.hashCode());
		result = prime * result
				+ ((manualTask == null) ? 0 : manualTask.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((person == null) ? 0 : person.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Job other = (Job) obj;
		if (autoTask == null) {
			if (other.autoTask != null)
				return false;
		} else if (!autoTask.equals(other.autoTask))
			return false;
		if (created == null) {
			if (other.created != null)
				return false;
		} else if (!created.equals(other.created))
			return false;
		if (due == null) {
			if (other.due != null)
				return false;
		} else if (!due.equals(other.due))
			return false;
		if (groupname == null) {
			if (other.groupname != null)
				return false;
		} else if (!groupname.equals(other.groupname))
			return false;
		if (manualTask == null) {
			if (other.manualTask != null)
				return false;
		} else if (!manualTask.equals(other.manualTask))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (person == null) {
			if (other.person != null)
				return false;
		} else if (!person.equals(other.person))
			return false;
		if (status != other.status)
			return false;
		return true;
	}
    
    
}
