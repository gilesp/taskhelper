package uk.co.vurt.hakken.server.connector;


import java.util.List;
import java.util.Properties;

public interface DataConnector {


	public String getInstances(String username);
	
	public List<DataConnectorTaskDefinition> getDefinitions();
	
	public DataConnectorTaskDefinition getDefinition(String name);
	
	public boolean createNew();
	
	public boolean save();
	
	
	
	
//	public List<String> getPropertyNames();
	
	public String getInfo();
	
//	public void init(Properties properties);
	
	public List<String> getDataItems();
}
