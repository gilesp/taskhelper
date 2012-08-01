package uk.co.vurt.hakken.server.mapping;

import java.io.Serializable;
import java.util.HashMap;
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

@Entity
@Table(name="dc_def_mappings")
public class DataConnectorTaskDefinitionMapping implements Serializable {

	private static final long serialVersionUID = 1824955271398493674L;
	
	/* Hibernate has a bug which means that the jpa schema is ignored in sequences
	 * so we need to manually include the schema name in the sequence name
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DC_DEFINITION_MAP_SEQ")
	@SequenceGenerator(name="DC_DEFINITION_MAP_SEQ", sequenceName="hakken.DC_DEFINITION_MAP_SEQ", schema="hakken")
	private long id;
	
	private String dataConnectorName;
	private String taskDefinitionName;
	
	@ElementCollection
    @MapKeyColumn(name="key")
    @Column(name="value")
    @CollectionTable(name="dc_def_mapping_props", joinColumns=@JoinColumn(name="dc_def_mapping_id"), schema="hakken")
	private Map<String, String> properties = new HashMap<String, String>();
	
	public void setProperty(String name, String value){
		properties.put(name, value);
	}
	
	public String getProperty(String name){
		return properties.get(name);
	}
	
	public Map<String, String> getProperties(){
		return properties;
	}

	public String getDataConnectorName() {
		return dataConnectorName;
	}

	public void setDataConnectorName(String dataConnectorName) {
		this.dataConnectorName = dataConnectorName;
	}

	public String getTaskDefinitionName() {
		return taskDefinitionName;
	}

	public void setTaskDefinitionName(String taskDefinitionName) {
		this.taskDefinitionName = taskDefinitionName;
	}

	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(dataConnectorName);
		builder.append(" - ");
		builder.append(taskDefinitionName);
		return builder.toString();
	}
	
}
