package uk.co.vurt.hakken.ui.widget;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

public abstract class AbstractLabelledWidget extends LinearLayout {

	protected TextView label;
	
	public AbstractLabelledWidget(Context context) {
		super(context);
		this.setOrientation(VERTICAL);
	}
	
	public void setLabel(String labelText){
		if(label != null){
			label.setText(labelText);
		}
	}
	
}
