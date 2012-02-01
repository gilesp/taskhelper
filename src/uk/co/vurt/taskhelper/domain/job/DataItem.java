package uk.co.vurt.taskhelper.domain.job;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class DataItem {

	private final static String TAG = "DataItem";
	
	String name;
	String type;
	String value;
	String pageName;
	
	public DataItem(){}
	
	public DataItem(String pageName, String name, String type, String value) {
		super();
		this.pageName = pageName;
		this.name = name;
		this.type = type;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}
	
	public JSONObject toJSON(){
		JSONObject data = new JSONObject();
		try {
			data.put("pageName", pageName);
			data.put("name", name);
			data.put("type", type);
			data.put("value", value);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return data;
	}
	
	public static DataItem valueOf(JSONObject dataItem){
		DataItem item = null;
		try{
			final String pageName = dataItem.getString("pageName");
			final String name = dataItem.getString("name");
			final String type = dataItem.getString("type");
			final String value = dataItem.getString("value") == null || dataItem.getString("value").equals("null") ? "" : dataItem.getString("value");
			item = new DataItem(pageName, name, type, value);
		} catch (final Exception e){
			Log.i(TAG, "Unable to parse JSON Job object: " + e.toString());
		}
		return item;
	}
}
