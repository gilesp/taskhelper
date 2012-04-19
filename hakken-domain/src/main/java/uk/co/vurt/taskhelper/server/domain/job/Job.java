package uk.co.vurt.taskhelper.server.domain.job;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import uk.co.vurt.taskhelper.server.domain.definition.StaticTaskDefinition;

public class Job implements Serializable{

	private static final long serialVersionUID = -4953683147051746800L;

	private String username;

    private StaticTaskDefinition manualTask;

    private Date created;

    private Date due;

    private Status status = Status.NEW;

    private String groupname = "Personal";
    
    private String name = "Undefined";
    
    private String notes;
    
    private Set<DataItem> dataItems = new HashSet<DataItem>();
    
    public void setManualTask(StaticTaskDefinition manualTask){
    	this.manualTask = manualTask;
    	this.name = manualTask.getName();
    }
    
    public StaticTaskDefinition getManualTask(){
    	return manualTask;
    }

}
