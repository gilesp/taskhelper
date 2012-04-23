package uk.co.vurt.hakken.server.connector;


import java.util.List;

public interface DataConnector {


	public String getInstances(DataConnectorTaskDefinition taskDefinition, String username);
	
	public List<DataConnectorTaskDefinition> getDefinitions();
	
	public DataConnectorTaskDefinition getDefinition(String name);
	
	public boolean createNew();
	
	public boolean save();
	
	
}
