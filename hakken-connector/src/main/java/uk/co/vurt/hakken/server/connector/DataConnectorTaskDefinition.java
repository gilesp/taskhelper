package uk.co.vurt.hakken.server.connector;


import java.util.List;

public abstract class DataConnectorTaskDefinition {

	protected static List<ConfigProperty> configProperties;
	protected String name;
	protected List<String> dataItemNames; 
	
	public String getName(){
		return this.name;
	}

	/**
	 * Maps DataConnector data item names to their equivalent Task data items
	 */
	public List<String> getDataItemNames() {
		return dataItemNames;
	}
	
	public List<ConfigProperty> getConfigProperties(){
		return configProperties;
	}
	
	
}
