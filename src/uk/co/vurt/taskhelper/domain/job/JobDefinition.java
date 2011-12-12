package uk.co.vurt.taskhelper.domain.job;

import java.util.Date;

import org.json.JSONObject;

import uk.co.vurt.taskhelper.domain.definition.TaskDefinition;

import android.util.Log;

public class JobDefinition {

	private final int id;
	private final String name;
	private final TaskDefinition definition;
	private final Date created;
	private final Date due;
	private String status;

	
	public JobDefinition(int id, String name, TaskDefinition definition, Date created,
			Date due, String status) {
		super();
		this.id = id;
		this.name = name;
		this.definition = definition;
		this.created = created;
		this.due = due;
		this.status = status;
	}

	public static JobDefinition valueOf(JSONObject job){
		try{
			final int id = job.getInt("id");
			final String name = job.getString("name");
			final TaskDefinition definition = TaskDefinition.valueOf(job.getJSONObject("task"));
			final Date created = new Date(job.getLong("created"));
			final Date due = new Date(job.getLong("due"));
			final String status = job.getString("status");

			return new JobDefinition(id, name, definition, created, due, status);
			
		} catch (final Exception e){
			Log.i("Job", "Unable to parse JSON Job object: " + e.toString());
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

	@Override
	public String toString() {
		return "JobDefinition [id=" + id + ", name=" + name + ", definition_id="
				+ definition.getId() + ", created=" + created + ", due=" + due
				+ ", status=" + status + "]";
	}
	
}
