package uk.co.vurt.taskhelper.ui.widget;

import uk.co.vurt.taskhelper.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.TextView;

public class LabelledCheckBox extends AbstractLabelledWidget {

	private CheckBox checkBox;
	
	public LabelledCheckBox(Context context, String labelText, boolean checked) {
		super(context);

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.labelled_check_box, this);
		
		label = (TextView)findViewById(R.id.labelled_check_box_label);
		checkBox = (CheckBox)findViewById(R.id.labelled_check_box_value);
		
		setLabel(labelText);
		
		if(checked){
			checkBox.setChecked(checked);
		}
	}
	
	public boolean getValue(){
		return checkBox.isChecked();
	}
	
	public void setValue(boolean checked){
		checkBox.setChecked(checked);
	}
}
