package uk.co.vurt.hakken.domain.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class TaskDefinition {

	private long id;
	private String name;
	private String description;
//	private List<Page> pages;
	private Map<String, Page> pages;
	
	public TaskDefinition(){
		super();
		pages = new  HashMap<String, Page>();
	}
	
	public TaskDefinition(long id, String name, String description, List<Page> pages) {
		this();
		this.id = id;
		this.name = name;
		this.description = description;
//		this.pages = pages;
		setPages(pages);
	}

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
//		this.pages = pages;
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
