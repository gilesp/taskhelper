package uk.co.vurt.hakken.server.connector;


import java.util.Map;

public abstract class DataConnectorTaskDefinition {

	protected static String name;
	protected static Map<String, String> dataitemNameMap;
	
	public String getName(){
		return this.name;
	}

	/**
	 * Maps DataConnector data item names to their equivalent Task data items
	 */
	public Map<String, String> getDataitemNameMap() {
		return dataitemNameMap;
	}
	
}
