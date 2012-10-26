package uk.co.vurt.hakken.ui.widget;

import java.io.Serializable;

import uk.co.vurt.hakken.R;
import android.content.Context;
import android.text.InputType;
import android.text.method.KeyListener;
import android.text.method.TextKeyListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

public class LabelledEditBox extends AbstractLabelledWidget implements Serializable{

	private EditText textBox;
	
	public LabelledEditBox(Context context, String labelText, String initialValue) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.labelled_edit_box, this);

		label = (TextView)findViewById(R.id.labelled_edit_box_label);
		textBox = (EditText)findViewById(R.id.labelled_edit_box_value);
		textBox.setSingleLine(true);
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
	
	public void setLines(int lines){
		textBox.setInputType(EditorInfo.TYPE_TEXT_FLAG_MULTI_LINE);
		textBox.setMinLines(lines);
		textBox.setGravity(Gravity.TOP);
		textBox.setVerticalScrollBarEnabled(true);
		textBox.setHorizontalScrollBarEnabled(false);
		textBox.setSingleLine(false);
	}
}
