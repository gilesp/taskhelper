package uk.co.vurt.hakken.ui.widget;

import uk.co.vurt.hakken.R;
import android.content.Context;
import android.text.method.KeyListener;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.TextView;

public class LabelledEditBox extends AbstractLabelledWidget {

	private EditText textBox;
	
	public LabelledEditBox(Context context, String labelText, String initialValue) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.labelled_edit_box, this);

		label = (TextView)findViewById(R.id.labelled_edit_box_label);
		textBox = (EditText)findViewById(R.id.labelled_edit_box_value);
		
		this.setOrientation(VERTICAL);

		setLabel(labelText);
		
		if(initialValue != null){
			textBox.setText(initialValue);
		}
	}

	public String getValue(){
		return textBox.getText().toString();
	}
	
	public void setValue(String value){
		textBox.setText(value);
	}
	
	public void setKeyListener(KeyListener input){
		textBox.setKeyListener(input);
	}
	
}
