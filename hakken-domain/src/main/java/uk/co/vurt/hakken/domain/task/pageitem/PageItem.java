package uk.co.vurt.hakken.domain.task.pageitem;

import java.util.List;
import java.util.Map;

public class PageItem {

	String name;
	String label;
	String type;
	String value;
	List<LabelledValue> values;
	
//	Map<String, String> attributes;
	Map attributes;
	
//	public PageItem(){}
//	
//	public PageItem(String name, String label, String type, String value) {
//		super();
//		this.name = name;
//		this.label = label;
//		this.type = type;
//		this.value = value;
//	}
//	
//	public PageItem(String name, String label, String type, String value, Map attributes) {
//		this(name, label, type, value);
//		this.attributes = attributes;
//	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public Map getAttributes() {
		return attributes;
	}

	public void setAttributes(Map attributes) {
		this.attributes = attributes;
	}

	public List<LabelledValue> getValues() {
		return values;
	}

	public void setValues(List<LabelledValue> values) {
		this.values = values;
	}

	@Override
	public String toString() {
		return "PageItem [name=" + name + ", label=" + label + ", type=" + type
				+ ", value=" + value + ", values=" + values + ", attributes="
				+ attributes + "]";
	}
	
	
//
//	@Override
//	public String toString() {
//		return "Item [name=" + name + ", type=" + type + ", label=" + label
//				+ ", value=" + value + "]";
//	}
	
	
}
