package uk.co.vurt.hakken.ui.widget;

import java.io.Serializable;

import uk.co.vurt.hakken.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LabelledDatePicker extends AbstractLabelledWidget implements Serializable{

	private EditText textBox;
	private Button pickDateButton;
  
	public LabelledDatePicker(Context context, String labelText, String initialValue) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.labelled_date_picker, this);
		
		label = (TextView)findViewById(R.id.labelled_date_picker_label);
		setLabel(labelText);
		
		textBox = (EditText)findViewById(R.id.labelled_date_picker_value);
		textBox.setEnabled(false);
		if(initialValue != null){
			textBox.setText(initialValue);
		}
		pickDateButton = (Button)findViewById(R.id.labelled_date_picker_button);

	}
	
	public void setOnClickListener(View.OnClickListener listener){
		pickDateButton.setOnClickListener(listener);
	}
	
	public String getValue(){
		return textBox.getText().toString();
	}
	
	public void setValue(String value){
		textBox.setText(value);
	}
}
