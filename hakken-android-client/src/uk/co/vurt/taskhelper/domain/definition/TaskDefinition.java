package uk.co.vurt.taskhelper.domain.definition;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;

import uk.co.vurt.taskhelper.providers.Task;

import android.util.Log;

public final class TaskDefinition {

	private final int id;
	private final String name;
	private final String description;
	private final List<Page> pages;
	
	
	public TaskDefinition(int id, String name, String description, List<Page> pages) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.pages = pages;
	}

	public static TaskDefinition valueOf(JSONObject definition){
		try{
			final int id = definition.getInt("id");
			final String name = definition.getString(Task.Definitions.NAME);
			final String description = definition.getString(Task.Definitions.DESCRIPTION);
			JSONArray pageArray = definition.getJSONArray(Task.Definitions.PAGES);
			ArrayList<Page> pages = new ArrayList<Page>();
			for(int i = 0; i < pageArray.length(); i++){
				pages.add(Page.valueOf(pageArray.getJSONObject(i)));
			}
			return new TaskDefinition(id, name, description, pages);
		}catch(final Exception e){
			Log.e("TaskDefinition", "Unable to parse JSON TaskDefinition object: " + e.toString());
		}
		return null;
	}
	
	public String toJson(){
		return new Gson().toJson(this);
	}
	
	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public int getId(){
		return id;
	}

	public List<Page> getPages() {
		return pages;
	}

	@Override
	public String toString() {
		return "TaskDefinition [id=" + id + ", name=" + name + ", description="
				+ description + ", pages=" + pages + "]";
	}



	
}
