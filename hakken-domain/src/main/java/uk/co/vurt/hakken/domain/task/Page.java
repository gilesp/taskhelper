package uk.co.vurt.hakken.domain.task;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import uk.co.vurt.hakken.domain.task.pageitem.PageItem;

public class Page {
	private String name;
	private String title;
	private LinkedHashMap<String, PageItem> items;
	private List<PageSelector> nextPages;
	
	private Map<String, String> attributes;
	
	public Page(){
		items = new LinkedHashMap<String, PageItem>();
	}
	
	public Page(String name, String title, List<PageItem> items) {
		super();
		this.name = name;
		this.title = title;
		setItems(items);
	}

	public Page(String name, String title, List<PageItem> items, List<PageSelector> nextPages){
		this(name, title, items);
		this.nextPages = nextPages;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<PageItem> getItems() {
		return new ArrayList<PageItem>(items.values());
	}

	public void setItems(List<PageItem> items) {
		this.items = new LinkedHashMap<String, PageItem>();
		for(PageItem item: items){
			addItem(item);
		}
	}

	public void addItem(PageItem item){
		this.items.put(item.getName(), item);
	}

	public PageItem getPageItem(String name){
		return items.get(name);
	}
	
	public List<PageSelector> getNextPages() {
		return nextPages;
	}

	public void setNextPages(List<PageSelector> nextPages) {
		this.nextPages = nextPages;
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}
	
	@Override
	public String toString() {
		return "Page [name=" + name + ", title=" + title + ", items=" + items
				+ ", nextPages=" + nextPages + ", attributes=" + attributes + "]";
	}
	
}