package uk.co.vurt.hakken.server.mapping;

import uk.co.vurt.hakken.domain.job.DataItem;
import uk.co.vurt.hakken.domain.task.TaskDefinition;
import uk.co.vurt.hakken.server.connector.DataConnector;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class ServiceMapping {

	DataConnector dataConnector;
	TaskDefinition taskDefinition;
	BiMap<String, DataItem> dataItemMappings;
	
	public ServiceMapping(){
		dataItemMappings = HashBiMap.<String, DataItem>create();
	}
	
	public DataConnector getDataConnector() {
		return dataConnector;
	}
	public void setDataConnector(DataConnector dataConnector) {
		this.dataConnector = dataConnector;
	}
	public TaskDefinition getTaskDefinition() {
		return taskDefinition;
	}
	public void setTaskDefinition(TaskDefinition taskDefinition) {
		this.taskDefinition = taskDefinition;
	}
	public void setMapping(String serviceDataItem, DataItem taskDataItem){
		dataItemMappings.forcePut(serviceDataItem, taskDataItem);
	}
	
	public DataItem getDataItem(String serviceDataItem){
		return dataItemMappings.get(serviceDataItem);
	}
	
	public String getServiceDataItem(DataItem dataItem){
		return dataItemMappings.inverse().get(dataItem);
	}
	
	
}
