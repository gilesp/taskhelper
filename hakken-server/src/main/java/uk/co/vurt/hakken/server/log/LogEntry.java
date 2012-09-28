package uk.co.vurt.hakken.server.log;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="logs")
@Access(value = AccessType.PROPERTY)
public class LogEntry implements Serializable{

	private static final long serialVersionUID = -6580535959518913375L;
	
	private Long id;
	private Date timestamp;
	private String message;
	private String username;
	
	public LogEntry(){}
	
	private LogEntry(Date timestamp, String username, String message){
		this.timestamp = timestamp;
		this.username = username;
		this.message = message;
	}
	
	public LogEntry(String username, String message){
		this(new Date(), username, message);
	}
	
	/* Hibernate has a bug which means that the jpa schema is ignored in sequences
	 * so we need to manually include the schema name in the sequence name
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="LOG_ENTRY_SEQ")
	@SequenceGenerator(name="LOG_ENTRY_SEQ", sequenceName="hakken.LOG_ENTRY_SEQ", schema="hakken")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	
}
