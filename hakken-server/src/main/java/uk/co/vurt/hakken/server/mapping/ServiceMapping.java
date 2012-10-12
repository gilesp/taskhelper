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
import javax.persistence.MapKey;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
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
	
	Map<String, EntrySet> connectorToTaskMappings;
	Map<String, String> taskToConnectorMappings;
	
	@Transient
	public Map<String,String> getTaskToConnectorMappings(){
		return taskToConnectorMappings;
	}
	
	@OneToMany(mappedBy="serviceMappingId")
	@MapKey(name="entryKey")
	public Map<String, EntrySet> getConnectorToTaskMappings(){
		return connectorToTaskMappings;
	}
	
	public void setConnectorToTaskMappings(Map<String, EntrySet> mappings){
		logger.info("setting Mappings");
		this.connectorToTaskMappings = mappings;
		taskToConnectorMappings = new HashMap<String, String>();
		List<String> mappingKeys = new ArrayList<String>(mappings.keySet());
		for(String connectorKey: mappingKeys){
			EntrySet taskDataItems = mappings.get(connectorKey);
			for(String taskDataItem: taskDataItems.entries){
				taskToConnectorMappings.put(taskDataItem, connectorKey);
			}
		}
	}
	
	public void setMapping(String connectorDataItem, String taskDataItem){
		logger.info("Setting mapping: " + connectorDataItem + ":" + taskDataItem);
		if(connectorToTaskMappings == null){
			connectorToTaskMappings = new HashMap<String, EntrySet>();
		}
		EntrySet entrySet;
		if(connectorToTaskMappings.containsKey(connectorDataItem)){
			entrySet = connectorToTaskMappings.get(connectorDataItem);
		} else {
			entrySet = new EntrySet();
			entrySet.setEntryKey(connectorDataItem);
		}
		entrySet.getEntries().add(taskDataItem);
		connectorToTaskMappings.put(connectorDataItem, entrySet);
		
		if(taskToConnectorMappings == null){
			taskToConnectorMappings = new HashMap<String, String>();
		}
		taskToConnectorMappings.put(taskDataItem, connectorDataItem);
	}
	
	public List<String> getTaskDataItem(String connectorDataItem){
		List<String> entries = new ArrayList<String>();
		if(connectorToTaskMappings.containsKey(connectorDataItem)){
			for(String entry: connectorToTaskMappings.get(connectorDataItem).entries){
				entries.add(entry);
			}
		}
		return entries;
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
