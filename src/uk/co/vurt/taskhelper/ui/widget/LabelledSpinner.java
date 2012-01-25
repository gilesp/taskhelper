package uk.co.vurt.taskhelper.ui.widget;

import uk.co.vurt.taskhelper.R;
import uk.co.vurt.taskhelper.domain.NameValue;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.Spinner;
import android.widget.TextView;

public class LabelledSpinner extends AbstractLabelledWidget {

	Spinner spinner;
	
	public LabelledSpinner(Context context, String labelText) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.labelled_spinner, this);
		
		label = (TextView)findViewById(R.id.labelled_spinner_label);
		spinner = (Spinner)findViewById(R.id.labelled_spinner_value);
		
		setLabel(labelText);
		spinner.setPrompt(labelText);
	}

	public void setAdapter(NameValueAdapter arrayAdapter) {
		spinner.setAdapter(arrayAdapter);
	}
	
	public void setSelected(NameValue nameValue){
		NameValueAdapter adapter = (NameValueAdapter)spinner.getAdapter();
		spinner.setSelection(adapter.getPosition(nameValue));
	}
	
	public NameValue getSelectedNameValue(){
		NameValueAdapter adapter = (NameValueAdapter)spinner.getAdapter();
		return adapter.getNameValue(spinner.getSelectedItemPosition());
	}
	
	public String getSelectedValue(){
		NameValueAdapter adapter = (NameValueAdapter)spinner.getAdapter();
		return adapter.getNameValue(spinner.getSelectedItemPosition()).getValue();
	}

}
