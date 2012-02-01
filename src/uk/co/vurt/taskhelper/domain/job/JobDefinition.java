package uk.co.vurt.taskhelper.domain.job;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import uk.co.vurt.taskhelper.domain.definition.TaskDefinition;

import android.util.Log;

public class JobDefinition {

	private final static String TAG = "JobDefinition";
	
	private final int id;
	private final String name;
	private final TaskDefinition definition;
	private final Date created;
	private final Date due;
	private String status;
	private String group;
	private String notes;
	private Set<DataItem> dataItems = new HashSet<DataItem>();
	
	public JobDefinition(int id, String name, TaskDefinition definition, Date created,
			Date due, String status, String notes) {
		super();
		this.id = id;
		this.name = name;
		this.definition = definition;
		this.created = created;
		this.due = due;
		this.status = status;
		this.notes = notes;
	}

	public JobDefinition(int id, String name, TaskDefinition definition, Date created, Date due, String status, String notes, String group){
		this(id, name, definition, created, due, status, notes);
		this.group = group;
	}
	
	public static JobDefinition valueOf(JSONObject job){
		try{
			final int id = job.getInt("id");
			final String name = job.getString("name");
			final TaskDefinition definition = TaskDefinition.valueOf(job.getJSONObject("task"));
			final Date created = new Date(job.getLong("created"));
			final Date due = new Date(job.getLong("due"));
			final String status = job.getString("status");
			String notes = null;
			if(job.has("notes")){
				notes = job.getString("notes");
			}
			
			JobDefinition jobDefinition;
			if(job.has("groupname") && !job.getString("groupname").equals("null")){
				final String group = job.getString("groupname");
				jobDefinition = new JobDefinition(id, name, definition, created, due, status, notes, group);
			}else{
				jobDefinition = new JobDefinition(id, name, definition, created, due, status, notes); 
			}
			
			if(job.has("dataItems")){
				JSONArray diArray = job.getJSONArray("dataItems");
				for(int i = 0; i < diArray.length(); i++){
					jobDefinition.getDataItems().add(DataItem.valueOf(diArray.getJSONObject(i)));
				}
			}
			return jobDefinition;
			
		} catch (final Exception e){
			Log.i(TAG, "Unable to parse JSON Job object: " + e.toString());
		}
		return null;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public TaskDefinition getDefinition() {
		return definition;
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

	public Set<DataItem> getDataItems() {
		return dataItems;
	}

	@Override
	public String toString() {
		return "JobDefinition [id=" + id + ", name=" + name + ", definition_id="
				+ definition.getId() + ", created=" + created + ", due=" + due
				+ ", status=" + status + "]";
	}
	
}
