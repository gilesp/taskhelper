package uk.co.vurt.hakken.ui.widget;

import java.io.Serializable;

import android.view.View;

public class WidgetWrapper implements Serializable {

	private static final long serialVersionUID = -6533675131580965386L;
	
	private View widget;
	private boolean required;
	private boolean readOnly;
	private boolean hidden;
	private String condition;
	
	public WidgetWrapper(View widget, boolean required, String condition, boolean readOnly, boolean hidden){
		this.widget = widget;
		this.required = required;
		if(condition != null && condition.length() > 0){
			this.condition = condition;
		}
		this.readOnly = readOnly;
		this.hidden = hidden;
	}
	
	public WidgetWrapper(View widget, boolean required){
		this(widget, required, null, false, false);
	}
	
	public WidgetWrapper(View widget){
		this(widget, false);
	}

	public View getWidget() {
		return widget;
	}

	public void setWidget(View widget) {
		this.widget = widget;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}
	
	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public boolean isReadOnly() {
		return readOnly;
	}
	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}
	public boolean isHidden() {
		return hidden;
	}
	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}
	public boolean hasCondition(){
		return condition != null && condition.length() > 0;
	}
}
