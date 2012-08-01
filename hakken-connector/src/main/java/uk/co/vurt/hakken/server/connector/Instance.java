package uk.co.vurt.hakken.server.connector;

import java.util.Date;
import java.util.Map;


public class Instance {

	private long id;
	private String name;
	private Date created;
	private Date due;
	private String notes;
	private Map<String, String> dataItems;
	
	public Instance(){}
	
	public Instance(long id, String name, Date created, Date due, String notes,
			Map<String, String> dataItems) {
		super();
		this.id = id;
		this.name = name;
		this.created = created;
		this.due = due;
		this.notes = notes;
		this.dataItems = dataItems;
	}

	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public Date getCreated() {
		return created;
	}
	public Date getDue() {
		return due;
	}
	public Map<String, String> getDataItems() {
		return dataItems;
	}
	public void setDataItems(Map<String, String> dataItems) {
		this.dataItems = dataItems;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Instance [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", created=");
		builder.append(created);
		builder.append(", due=");
		builder.append(due);
		builder.append(", notes=");
		builder.append(notes);
		builder.append(", dataItems=");
		builder.append(dataItems);
		builder.append("]");
		return builder.toString();
	}
	
	
}
