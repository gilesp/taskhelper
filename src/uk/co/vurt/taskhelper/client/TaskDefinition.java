package uk.co.vurt.taskhelper.client;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import uk.co.vurt.taskhelper.providers.Task;

import android.util.Log;

public final class TaskDefinition {

	private final int id;
	private final String name;
	private final String description;
	private final List<TaskDefinition.Page> pages;
	
	
	public TaskDefinition(int id, String name, String description, List<TaskDefinition.Page> pages) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.pages = pages;
	}

	public static TaskDefinition valueOf(JSONObject definition){
		try{
			final int id = definition.getInt(Task.Definitions._ID);
			final String name = definition.getString(Task.Definitions.NAME);
			final String description = definition.getString(Task.Definitions.DESCRIPTION);
			JSONArray pageArray = definition.getJSONArray(Task.Definitions.PAGES);
			ArrayList<TaskDefinition.Page> pages = new ArrayList<TaskDefinition.Page>();
			for(int i = 0; i < pageArray.length(); i++){
				pages.add(Page.valueOf(pageArray.getJSONObject(i)));
			}
			return new TaskDefinition(id, name, description, pages);
		}catch(final Exception e){
			Log.e("TaskDefinition", "Unable to parse JSON TaskDefinition object: " + e.toString());
		}
		return null;
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

	public List<TaskDefinition.Page> getPages() {
		return pages;
	}



	public final static class Page {
		private final String name;
		
		public Page(String name) {
			super();
			this.name = name;
		}

		public String getName() {
			return name;
		}
		
		public static Page valueOf(JSONObject page){
			try{
				final String name = page.getString("name");
				return new Page(name);
			}catch(final Exception e){
				Log.i("Page", "Unable to parse JSON Page object: " + e.toString());
			}
			return null;
		}
	}
}
