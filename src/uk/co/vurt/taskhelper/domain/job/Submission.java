package uk.co.vurt.taskhelper.domain.job;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
	
	public JSONObject toJSON(){
		JSONObject data = new JSONObject();
		try {
			data.put("username", username);
			data.put("jobId", jobId);
			JSONArray diArray = new JSONArray();
			for(DataItem dataItem: dataItems){
				diArray.put(dataItem.toJSON());
			}
			data.put("dataitems", diArray);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return data;
	}
}
