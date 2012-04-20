package uk.co.vurt.hakken.domain.task;

import java.util.List;

public final class TaskDefinition {

	private int id;
	private String name;
	private String description;
	private List<Page> pages;

	public TaskDefinition(){}
	
	public TaskDefinition(int id, String name, String description, List<Page> pages) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.pages = pages;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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
		return pages;
	}

	public void setPages(List<Page> pages) {
		this.pages = pages;
	}

	@Override
	public String toString() {
		return "TaskDefinition [id=" + id + ", name=" + name + ", description="
				+ description + ", pages=" + pages + "]";
	}

}
