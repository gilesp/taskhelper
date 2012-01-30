package uk.co.vurt.taskhelper.ui.widget;

import java.util.ArrayList;
import java.util.List;

import uk.co.vurt.taskhelper.R;
import uk.co.vurt.taskhelper.domain.NameValue;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class LabelledSpinner extends AbstractLabelledWidget {

	Context context;
	Spinner spinner;
	boolean multiSelect = false;
	
	public LabelledSpinner(Context context, String labelText) {
		this(context, labelText, false);
	}
	
	public LabelledSpinner(Context context, String labelText, boolean multiSelect){
		super(context);
		this.context = context;
		this.multiSelect = multiSelect;
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		if(multiSelect){
			inflater.inflate(R.layout.labelled_multiselect_spinner, this);
			spinner = (MultiSelectSpinner)findViewById(R.id.labelled_spinner_value);
		}else{
			inflater.inflate(R.layout.labelled_spinner, this);
			spinner = (Spinner)findViewById(R.id.labelled_spinner_value);
		}
		label = (TextView)findViewById(R.id.labelled_spinner_label);
		setLabel(labelText);
		spinner.setPrompt(labelText);
	}
	
	public void setItems(List<NameValue> items){
		if(multiSelect){
			((MultiSelectSpinner)spinner).setItems(items, "Please select...");
		}else{
			NameValueAdapter arrayAdapter = new NameValueAdapter(context, R.layout.spinner_item, items);
			spinner.setAdapter(arrayAdapter);
		}
	}
	
	public void setSelected(NameValue nameValue){
		if(multiSelect){
			ArrayAdapter<String> adapter = (ArrayAdapter<String>)spinner.getAdapter();
			spinner.setSelection(adapter.getPosition(nameValue.getName()));
		} else {
			NameValueAdapter adapter = (NameValueAdapter)spinner.getAdapter();
			spinner.setSelection(adapter.getPosition(nameValue));
		}
	}
	
	public NameValue getSelectedNameValue(){
		NameValueAdapter adapter = (NameValueAdapter)spinner.getAdapter();
		return adapter.getNameValue(spinner.getSelectedItemPosition());
	}
	
	public String getSelectedValue(){
		NameValueAdapter adapter = (NameValueAdapter)spinner.getAdapter();
		return adapter.getNameValue(spinner.getSelectedItemPosition()).getValue();
	}

	public List<NameValue> getSelectedNameValues(){
		if(multiSelect){
			return ((MultiSelectSpinner)spinner).getSelectedItems();
		}else{
			ArrayList<NameValue> result = new ArrayList<NameValue>();
			result.add(getSelectedNameValue());
			return result;
		}
	}
	
	public String[] getSelectedValues(){
		List<NameValue> selectedItems = getSelectedNameValues();
		String[] selectedValues = new String[selectedItems.size()];
		int i = 0;
		for(NameValue item: selectedItems){
			selectedValues[i] = item.getValue();
			i++;
		}
		return selectedValues;
	}

	public boolean isMultiSelect() {
		return multiSelect;
	}
}
