package uk.co.vurt.hakken.server.connector;


import java.util.Date;
import java.util.List;
import java.util.Map;

import uk.co.vurt.hakken.domain.job.Submission;

public interface DataConnector<T extends DataConnectorTaskDefinition> {


	public abstract List<Instance> getInstances(T taskDefinition, Map<String, String> properties, String username, Date lastUpdated);
	
	public List<T> getDefinitions();
	
	public T getDefinition(String name);
	
	public boolean createNew();
	
	public boolean save(Submission submission, Map<String, String> taskToConnectorMappings, String taskDefinitionName);
	
	public String getName();
	
	public String getType();
	
//	public List<String> getPropertyNames();
	
	public String getInfo();
	
//	public void init(Properties properties);
//	
//	public List<String> getDataItems();
}
