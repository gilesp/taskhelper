package uk.co.vurt.hakken.server.connector;


import java.util.Date;
import java.util.List;
import java.util.Map;

import uk.co.vurt.hakken.domain.job.Submission;
import uk.co.vurt.hakken.domain.job.SubmissionStatus;
import uk.co.vurt.hakken.domain.task.TaskDefinition;

public interface DataConnector<T extends DataConnectorTaskDefinition> {


	public abstract List<Instance> getInstances(T taskDefinition, Map<String, String> properties, String username, Date lastUpdated);
	
	public List<T> getDefinitions();
	
	public T getDefinition(String name);
	
	public boolean createNew();
	
	public SubmissionStatus save(Submission submission, Map<String, String> taskToConnectorMappings, TaskDefinition taskDefinition);
	
	public String getName();
	
	public String getType();
	
//	public List<String> getPropertyNames();
	
	public String getInfo();
	
	
//	public void init(Properties properties);
//	
//	public List<String> getDataItems();
}
