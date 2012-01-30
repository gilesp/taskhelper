package uk.co.vurt.taskhelper.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import uk.co.vurt.taskhelper.R;
import uk.co.vurt.taskhelper.domain.NameValue;
import uk.co.vurt.taskhelper.domain.definition.PageItem;
import uk.co.vurt.taskhelper.domain.job.DataItem;
import uk.co.vurt.taskhelper.ui.widget.LabelledCheckBox;
import uk.co.vurt.taskhelper.ui.widget.LabelledDatePicker;
import uk.co.vurt.taskhelper.ui.widget.LabelledEditBox;
import uk.co.vurt.taskhelper.ui.widget.LabelledSpinner;
import uk.co.vurt.taskhelper.ui.widget.NameValueAdapter;
import android.content.Context;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class WidgetFactory {

	private static final String TAG = "WidgetFactory";
	
	public static View createWidget(Context context, PageItem item,
			DataItem dataItem) {

		View widget = null;

		// create new widget and add it to the map
		if ("LABEL".equals(item.getType())) {
			TextView label = new TextView(context);
			label.setText(item.getLabel());
			widget = label;
		} else if ("TEXT".equals(item.getType())) {
			LabelledEditBox editBox = new LabelledEditBox(context,
					item.getLabel(), dataItem != null ? dataItem.getValue()
							: item.getValue());
			widget = editBox;
		} else if ("DIGITS".equals(item.getType())
				|| "NUMERIC".equals(item.getType())) {
			LabelledEditBox editBox = new LabelledEditBox(context,
					item.getLabel(), dataItem != null ? dataItem.getValue()
							: item.getValue());
			editBox.setKeyListener(new DigitsKeyListener());
			widget = editBox;
		} else if ("DATETIME".equals(item.getType())) {
			final LabelledDatePicker datePicker = new LabelledDatePicker(
					context, item.getLabel(),
					dataItem != null ? dataItem.getValue() : item.getValue());
			// datePicker.setOnClickListener(new View.OnClickListener() {
			// @Override
			// public void onClick(View v) {
			// showDatePickerDialog(datePicker);
			// }
			// });
			widget = datePicker;
		} else if ("YESNO".equals(item.getType())) {
			LabelledCheckBox checkBox = new LabelledCheckBox(
					context,
					item.getLabel(),
					dataItem != null ? Boolean.parseBoolean(dataItem.getValue())
							: false);
			widget = checkBox;
		} else if ("SELECT".equals(item.getType())) {
			boolean multiSelect = false;
			if(item.getAttributes() != null && item.getAttributes().containsKey("multiselect")){
				Log.d(TAG, "multiselect: " + item.getAttributes().get("multiselect"));				
				multiSelect = Boolean.parseBoolean(item.getAttributes().get("multiselect"));
			}
			LabelledSpinner spinner = new LabelledSpinner(context, item.getLabel(), multiSelect);
			//At this point, the value is just a string containing json.
			//we need to convert that to something usable by the spinner.
			ArrayList<NameValue> spinnerArray = new ArrayList<NameValue>();
			spinnerArray.add(new NameValue("",null)); //add a blank entry to avoid the first item being pre-selected.

			ArrayList<String> dataItemValues = new ArrayList<String>();
			if(dataItem != null){
				if(dataItem.getValue().contains(",")){
					//multiselect value
					dataItemValues.addAll(Arrays.asList(dataItem.getValue().split("[,]")));
				} else {
					dataItemValues.add(dataItem.getValue());
				}
			}
			List<NameValue> selected = new ArrayList<NameValue>();
			try {
				JSONArray valueArray = new JSONArray(item.getValue());
				for(int i = 0; i < valueArray.length(); i++){
					JSONObject labelledValue = valueArray.getJSONObject(i);
					if(labelledValue.has("label")){
						NameValue nameValue = new NameValue(labelledValue.getString("label"), labelledValue.getString("value"));
						if(dataItemValues.contains(nameValue.getValue())){
							selected.add(nameValue);
						}
						spinnerArray.add(nameValue);
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			spinner.setItems(spinnerArray);
//			NameValueAdapter arrayAdapter = new NameValueAdapter(context, R.layout.spinner_item, spinnerArray);
//			spinner.setAdapter(arrayAdapter);
			for(NameValue selectedValue: selected){
				spinner.setSelected(selectedValue);
			}
			widget = spinner;
		} else {
			TextView errorLabel = new TextView(context);
			errorLabel.setText("Unknown item: '" + item.getType() + "'");
			widget = errorLabel;
		}

		return widget;
	}
}
