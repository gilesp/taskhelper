package uk.co.vurt.hakken.ui.widget;

import android.view.View;

public class WidgetWrapper {

	private View widget;
	private boolean required;
	private boolean readOnly;
	
	public WidgetWrapper(View widget, boolean required, boolean readOnly){
		this.widget = widget;
		this.required = required;
		this.readOnly = readOnly;
	}
	public WidgetWrapper(View widget, boolean required){
		this(widget, required, false);
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
	public boolean isReadOnly() {
		return readOnly;
	}
	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}
	
}
