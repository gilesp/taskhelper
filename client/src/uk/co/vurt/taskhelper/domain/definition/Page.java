package uk.co.vurt.taskhelper.domain.definition;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import uk.co.vurt.taskhelper.providers.Task;

import android.util.Log;

public class Page {
	private final static String TAG = "Page";
	private final String name;
	private final String title;
	private final List<PageItem> items;
	
	public Page(String name, String title, List<PageItem> items) {
		super();
		this.name = name;
		this.title = title;
		this.items = items;
	}

	public String getName() {
		return name;
	}
	
	
	public List<PageItem> getItems() {
		return items;
	}

	
	public String getTitle() {
		return title;
	}

	public static Page valueOf(JSONObject page){
		try{
			final String name = page.getString("name");
			final String title;
			if(page.has("title")){
				title = page.getString("title");
			}else{
				title = "";
			}
			
			JSONArray pageItemArray = page.getJSONArray(Task.Definitions.PAGE_ITEMS);
			ArrayList<PageItem> pageItems = new ArrayList<PageItem>();
			for(int i = 0; i < pageItemArray.length(); i++){
				pageItems.add(PageItem.valueOf(pageItemArray.getJSONObject(i)));
			}
			return new Page(name, title, pageItems);
		}catch(final Exception e){
			Log.i(TAG, "Unable to parse JSON Page object: " + e.toString());
		}
		return null;
	}
}