package uk.co.vurt.hakken.domain.task;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.TreeMap;

import org.json.simple.JSONObject;

public final class TaskDefinition {

	private long id;
	private String name;
	private String description;
	private LinkedHashMap<String, Page> pages;
	
	public TaskDefinition(){
		super();
		pages = new LinkedHashMap<String, Page>();
	}
	
	public TaskDefinition(long id, String name, String description, List<Page> pages) {
		this();
		this.id = id;
		this.name = name;
		this.description = description;
		setPages(pages);
	}
	
//	// TODO: RP/Kash - DONE - finish valueof method?
//	public static TaskDefinition valueOf(JSONObject task) {
//		final int id = (Integer) task.get("id");
//		final String name = (String) task.get("name");
//		final String description = (String) task.get("description");
//		final List<Page> pages = (List<Page>) task.get("pages");
//		TaskDefinition taskDefinition;
//		return new TaskDefinition(id, name, description, pages);
//	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Page> getPages() {
		return new ArrayList<Page>(pages.values());
	}

	public void setPages(List<Page> pages) {
		this.pages = new LinkedHashMap<String, Page>();
		for(Page page: pages){
			this.pages.put(page.getName(), page);
		}
	}

	public Page getPage(String name){
		return pages.get(name);
	}
	
	@Override
	public String toString() {
		return "TaskDefinition [id=" + id + ", name=" + name + ", description="
				+ description + ", pages=" + pages + "]";
	}



}
