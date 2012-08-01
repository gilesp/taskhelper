package uk.co.vurt.hakken.server.mapping;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.vurt.hakken.domain.job.DataItem;

@Entity
@Table(name="service_mappings")
@Access(value = AccessType.PROPERTY)
public class ServiceMapping implements Serializable{

	private static final Logger logger = LoggerFactory.getLogger(ServiceMapping.class);
	private static final long serialVersionUID = -2494689555896279648L;
	private long id;
	DataConnectorTaskDefinitionMapping dataConnectorTaskDefinitionMapping;
	String taskDefinitionName;
	Map<String, String> connectorToTaskMappings;
	Map<String, String> taskToConnectorMappings;
	
	@Transient
	public Map<String,String> getTaskToConnectorMappings(){
		return taskToConnectorMappings;
	}
	
	@ElementCollection
    @MapKeyColumn(name="connector_di")
    @Column(name="task_di")
    @CollectionTable(name="service_mapping_dataitems", joinColumns=@JoinColumn(name="mapping_id"), schema="hakken")
	public Map<String, String> getConnectorToTaskMappings(){
		return connectorToTaskMappings;
	}

	public void setConnectorToTaskMappings(Map<String, String> mappings){
		logger.info("setting Mappings");
		connectorToTaskMappings = new HashMap<String, String>();
		taskToConnectorMappings = new HashMap<String, String>();
		List<String> mappingKeys = new ArrayList<String>(mappings.keySet());
		for(String connectorKey: mappingKeys){
			setMapping(connectorKey, mappings.get(connectorKey));
		}
	}
	
	public void setMapping(String connectorDataItem, String taskDataItem){
		logger.info("Setting mapping: " + connectorDataItem + ":" + taskDataItem);
		if(connectorToTaskMappings == null){
			connectorToTaskMappings = new HashMap<String, String>();
		}
		connectorToTaskMappings.put(connectorDataItem, taskDataItem);
		if(taskToConnectorMappings == null){
			taskToConnectorMappings = new HashMap<String, String>();
		}
		taskToConnectorMappings.put(taskDataItem, connectorDataItem);
	}
	
	public String getTaskDataItem(String connectorDataItem){
		return connectorToTaskMappings.get(connectorDataItem);
	}
	
	public String getServiceDataItem(DataItem dataItem){
		return taskToConnectorMappings.get(dataItem.getName());
	}
	
	public String getTaskDefinitionName() {
		return taskDefinitionName;
	}

	public void setTaskDefinitionName(String taskDefinitionName) {
		this.taskDefinitionName = taskDefinitionName;
	}

	/* Hibernate has a bug which means that the jpa schema is ignored in sequences
	 * so we need to manually include the schema name in the sequence name
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SERV_MAP_SEQ")
	@SequenceGenerator(name="SERV_MAP_SEQ", sequenceName="hakken.SERV_MAP_SEQ", schema="hakken")
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@OneToOne
	@JoinColumn(name="DC_DEF_MAPPING_ID")
	public DataConnectorTaskDefinitionMapping getDataConnectorTaskDefinitionMapping() {
		return dataConnectorTaskDefinitionMapping;
	}

	public void setDataConnectorTaskDefinitionMapping(
			DataConnectorTaskDefinitionMapping dataConnectorTaskDefinitionMapping) {
		this.dataConnectorTaskDefinitionMapping = dataConnectorTaskDefinitionMapping;
	}
	
}
