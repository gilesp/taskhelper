package uk.co.vurt.hakken.domain.task.pageitem;

public class LabelledValue {

	String label;
	String value;
	
	public String getLabel() {
		return label;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "LabelledValue [label=" + label + ", value=" + value + "]";
	}
	
}
