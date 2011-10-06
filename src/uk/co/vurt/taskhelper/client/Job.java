package uk.co.vurt.taskhelper.client;

import org.json.JSONObject;

import android.util.Log;

public final class Job {

	private final String name;
	private final String description;
	private final int id;
	
	public Job(String name, String description, int id) {
		super();
		this.name = name;
		this.description = description;
		this.id = id;
	}
	
	public static Job valueOf(JSONObject job){
		try{
			final String name = job.getString("name");
			final String description = job.getString("description");
			final int id = job.getInt("id");
			return new Job(name, description, id);
		} catch (final Exception e){
			Log.i("Job", "Unable to parse JSON Job object: " + e.toString());
		}
		return null;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public int getId() {
		return id;
	}
	
	
}
