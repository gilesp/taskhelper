package uk.co.vurt.hakken.server.mapping;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="service_mapping_entries")
public class EntrySet implements Serializable{

	/* Hibernate has a bug which means that the jpa schema is ignored in sequences
	 * so we need to manually include the schema name in the sequence name
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SERV_ENTRIES_SEQ")
	@SequenceGenerator(name="SERV_ENTRIES_SEQ", sequenceName="hakken.SERV_ENTRIES_SEQ", schema="hakken")
	protected Long id;
	
	protected Long serviceMappingId;
	
	protected String entryKey;
	
	@ElementCollection
	protected Set<String> entries = new HashSet<String>();
	
	public String getEntryKey() {
		return entryKey;
	}
	
	public void setEntryKey(String entryKey) {
		this.entryKey = entryKey;
	}
	
	public Set<String> getEntries() {
		return entries;
	}
	
	public void setEntries(Set<String> entries) {
		this.entries = entries;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getServiceMappingId() {
		return serviceMappingId;
	}

	public void setServiceMappingId(Long serviceMappingId) {
		this.serviceMappingId = serviceMappingId;
	}
	
}
