package uk.co.vurt.hakken.server.mapping;

import java.util.Collection;
import java.util.Iterator;

import uk.co.vurt.hakken.domain.job.DataItem;
import uk.co.vurt.hakken.domain.task.TaskDefinition;
import uk.co.vurt.hakken.server.connector.DataConnector;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class ServiceMapping {

	DataConnector dataConnector;
	TaskDefinition taskDefinition;
	BiMap<String, String> dataItemMappings;
	
	public ServiceMapping(){
		dataItemMappings = HashBiMap.<String, String>create();
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
	public void setMapping(String connectorDataItem, String taskDataItem){
		dataItemMappings.forcePut(connectorDataItem, taskDataItem);
	}
	
	public String getTaskDataItem(String connectorDataItem){
		return dataItemMappings.get(connectorDataItem);
	}
	
	public String getServiceDataItem(DataItem dataItem){
		return dataItemMappings.inverse().get(dataItem);
	}

	@Override
	public String toString() {
		final int maxLen = 10;
		StringBuilder builder = new StringBuilder();
		builder.append("ServiceMapping [dataConnector=");
		builder.append(dataConnector);
		builder.append(", taskDefinition=");
		builder.append(taskDefinition);
		builder.append(", dataItemMappings=");
		builder.append(dataItemMappings != null ? toString(
				dataItemMappings.entrySet(), maxLen) : null);
		builder.append("]");
		return builder.toString();
	}

	private String toString(Collection<?> collection, int maxLen) {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		int i = 0;
		for (Iterator<?> iterator = collection.iterator(); iterator.hasNext()
				&& i < maxLen; i++) {
			if (i > 0)
				builder.append(", ");
			builder.append(iterator.next());
		}
		builder.append("]");
		return builder.toString();
	}
	
	
}
