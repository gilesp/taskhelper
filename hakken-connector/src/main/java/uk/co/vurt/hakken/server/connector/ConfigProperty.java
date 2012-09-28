package uk.co.vurt.hakken.server.connector;

public class ConfigProperty {

	private String name;
	private String value;
	private boolean dataItem;
	
	public ConfigProperty(){}
	
	public ConfigProperty(String name, boolean dataItem){
		this.name = name;
		this.dataItem = dataItem;
	}
	
	public ConfigProperty(String name, String value, boolean dataItem) {
		super();
		this.name = name;
		this.value = value;
		this.dataItem = dataItem;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public boolean isDataItem() {
		return dataItem;
	}
	public void setDataItem(boolean dataItem) {
		this.dataItem = dataItem;
	}
	
	
}
