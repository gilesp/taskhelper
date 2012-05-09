package uk.co.vurt.hakken.server.mapping;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import uk.co.vurt.hakken.domain.job.DataItem;
import uk.co.vurt.hakken.domain.task.TaskDefinition;
import uk.co.vurt.hakken.server.connector.DataConnector;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

@Entity
@Table(name="service_mappings")
public class ServiceMapping implements Serializable{

	private static final long serialVersionUID = -2494689555896279648L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SERV_MAP_SEQ")
	@SequenceGenerator(name="SERV_MAP_SEQ", sequenceName="SERV_MAP_SEQ", allocationSize=10)
	private long id;

	@Transient
	DataConnector dataConnector;

	String dataConnectorName;
	
	@Transient
	TaskDefinition taskDefinition;
	
	String taskDefinitionName;
	
	@ElementCollection
    @MapKeyColumn(name="connector_di")
    @Column(name="task_di")
    @CollectionTable(name="service_mapping_dataitems", joinColumns=@JoinColumn(name="mapping_id"))
	Map<String, String> dataItemMappings;
	
	public ServiceMapping(){
		dataItemMappings = HashBiMap.<String, String>create();
	}
	
	public DataConnector getDataConnector() {
		return dataConnector;
	}
	public void setDataConnector(DataConnector dataConnector) {
		this.dataConnector = dataConnector;
		setDataConnectorName(dataConnector.getName());
	}
	
	public String getDataConnectorName() {
		return dataConnectorName;
	}

	public void setDataConnectorName(String dataConnectorName) {
		this.dataConnectorName = dataConnectorName;
	}

	public TaskDefinition getTaskDefinition() {
		return taskDefinition;
	}
	public void setTaskDefinition(TaskDefinition taskDefinition) {
		this.taskDefinition = taskDefinition;
		setTaskDefinitionName(taskDefinition.getName());
	}
	
	public void setMapping(String connectorDataItem, String taskDataItem){
		((BiMap<String,String>)dataItemMappings).forcePut(connectorDataItem, taskDataItem);
	}
	
	public String getTaskDataItem(String connectorDataItem){
		return dataItemMappings.get(connectorDataItem);
	}
	
	public String getServiceDataItem(DataItem dataItem){
		return ((BiMap<String,String>)dataItemMappings).inverse().get(dataItem);
	}
	
	public String getTaskDefinitionName() {
		return taskDefinitionName;
	}

	public void setTaskDefinitionName(String taskDefinitionName) {
		this.taskDefinitionName = taskDefinitionName;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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
