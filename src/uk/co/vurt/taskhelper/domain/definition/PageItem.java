package uk.co.vurt.taskhelper.domain.definition;

import org.json.JSONObject;

import android.util.Log;

public class PageItem {

	String name;
	String label;
	String type;
	String value;
	
	public PageItem(){}
	
	public PageItem(String name, String label, String type, String value) {
		super();
		this.name = name;
		this.label = label;
		this.type = type;
		this.value = value;
	}
	
	public static PageItem valueOf(JSONObject jsonObject){
		try{
			PageItem item = new PageItem();
			if(jsonObject.has("name")){
				item.setName(jsonObject.getString("name"));
			}
			if(jsonObject.has("type")){
				item.setType(jsonObject.getString("type"));
			}
			if(jsonObject.has("label")){
				item.setLabel(jsonObject.getString("label"));
			}
			if(jsonObject.has("value")){
				item.setValue(jsonObject.getString("value"));
			}
			return item;
		}catch(final Exception e){
			Log.i("Page", "Unable to parse JSON PageItem object: " + e.toString());
		}
		return null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
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
	@Override
	public String toString() {
		return "Item [name=" + name + ", type=" + type + ", label=" + label
				+ ", value=" + value + "]";
	}
	
	
}
