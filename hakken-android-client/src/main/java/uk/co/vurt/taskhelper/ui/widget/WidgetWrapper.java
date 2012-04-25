package uk.co.vurt.taskhelper.ui.widget;

import android.view.View;

public class WidgetWrapper {

	private View widget;
	private boolean required;
	
	public WidgetWrapper(View widget, boolean required){
		this.widget = widget;
		this.required = required;
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
	
}
