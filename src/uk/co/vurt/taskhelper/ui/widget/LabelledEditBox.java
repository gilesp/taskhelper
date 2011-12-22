package uk.co.vurt.taskhelper.ui.widget;

import android.content.Context;
import android.text.method.KeyListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LabelledEditBox extends LinearLayout {

	TextView label;
	EditText textBox;
	
	public LabelledEditBox(Context context, String labelText, String initialValue) {
		super(context);
		label = new TextView(context);
		label.setText(labelText);
		textBox = new EditText(context);
		if(initialValue != null){
			textBox.setText(initialValue);
		}
		textBox.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		this.addView(label);
		this.addView(textBox);
	}

	public String getValue(){
		return textBox.getText().toString();
	}
	
	public void setValue(String value){
		textBox.setText(value);
	}
	
	public void setLabel(String labelText){
		label.setText(labelText);
	}
	
	public void setKeyListener(KeyListener input){
		textBox.setKeyListener(input);
	}
}
