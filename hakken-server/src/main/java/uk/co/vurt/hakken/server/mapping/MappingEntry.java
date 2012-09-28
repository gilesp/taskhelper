package uk.co.vurt.hakken.server.mapping;
/**
 * @deprecated 
 * @author giles.paterson
 *
 */
public class MappingEntry {

	String from;
	String to;
	
	public MappingEntry(){}
	
	public MappingEntry(String from, String to) {
		super();
		this.from = from;
		this.to = to;
	}
	
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	
	
}
