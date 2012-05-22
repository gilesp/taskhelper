package uk.co.vurt.hakken.server.connector;


import java.util.Date;
import java.util.List;
import java.util.Map;

public interface DataConnector<T extends DataConnectorTaskDefinition> {


	public abstract List<Instance> getInstances(T taskDefinition, Map<String, String> properties, String username, Date lastUpdated);
	
	public List<T> getDefinitions();
	
	public T getDefinition(String name);
	
	public boolean createNew();
	
	public boolean save();
	
	public String getName();
	
	
	
//	public List<String> getPropertyNames();
	
	public String getInfo();
	
//	public void init(Properties properties);
//	
//	public List<String> getDataItems();
}
