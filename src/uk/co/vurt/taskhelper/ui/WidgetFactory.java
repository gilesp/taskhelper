package uk.co.vurt.taskhelper.ui;

import java.util.ArrayList;

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
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class WidgetFactory {

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
			LabelledSpinner spinner = new LabelledSpinner(context, item.getLabel());
			//At this point, the value is just a string containing json.
			//we need to convert that to something usable by the spinner.
//			ArrayList<String> spinnerArray = new ArrayList<String>();
			ArrayList<NameValue> spinnerArray = new ArrayList<NameValue>();
			spinnerArray.add(new NameValue("",null)); //add a blank entry to avoid the first item being pre-selected.
			NameValue selected = null;
			try {
				JSONArray valueArray = new JSONArray(item.getValue());
				for(int i = 0; i < valueArray.length(); i++){
					JSONObject labelledValue = valueArray.getJSONObject(i);
					if(labelledValue.has("label")){
						NameValue nameValue = new NameValue(labelledValue.getString("label"), labelledValue.getString("value"));
						if(dataItem != null && nameValue.getValue().equals(dataItem.getValue())){
							selected = nameValue;
						}
						spinnerArray.add(nameValue);
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, spinnerArray);
			NameValueAdapter arrayAdapter = new NameValueAdapter(context, R.layout.spinner_item, spinnerArray);
			spinner.setAdapter(arrayAdapter);
			if(selected != null){
				spinner.setSelected(selected);
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
