package uk.co.vurt.hakken.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import uk.co.vurt.hakken.R;
import uk.co.vurt.hakken.domain.NameValue;
import uk.co.vurt.hakken.domain.job.DataItem;
import uk.co.vurt.hakken.domain.task.pageitem.LabelledValue;
import uk.co.vurt.hakken.domain.task.pageitem.PageItem;
import uk.co.vurt.hakken.processor.PageItemProcessor;
import uk.co.vurt.hakken.ui.widget.LabelledCheckBox;
import uk.co.vurt.hakken.ui.widget.LabelledDatePicker;
import uk.co.vurt.hakken.ui.widget.LabelledEditBox;
import uk.co.vurt.hakken.ui.widget.LabelledSpinner;
import uk.co.vurt.hakken.ui.widget.WidgetWrapper;
import android.content.Context;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * This class is messy. Needs reimplementing with a nice map of individual widget creators
 * keyed on the item type, or something like that.
 * 
 *
 */
public class WidgetFactory {

	private static final String TAG = "WidgetFactory";
	
	
	
	public static WidgetWrapper createWidget(Context context, PageItem item,
			DataItem dataItem) {

		View widget = null;

		boolean readonly = PageItemProcessor.getBooleanAttribute(item, "readonly");
		boolean hidden = PageItemProcessor.getBooleanAttribute(item, "hidden");
		boolean required = PageItemProcessor.getBooleanAttribute(item, "required");
		String condition = PageItemProcessor.getStringAttribute(item, "condition");
		
		String defaultValue = item.getValue();
		if(dataItem != null){
			defaultValue = dataItem.getValue();
		}
		
		// create new widget and add it to the map
		if ("LABEL".equals(item.getType())) {
			TextView label = new TextView(context);
			label.setText(item.getLabel());
			widget = label;
		} else if ("TEXT".equals(item.getType())) {
			if(defaultValue == null){
				defaultValue = "";
			}
			if(readonly){
				TextView label = new TextView(context);
				label.setText(item.getLabel() + ": " + defaultValue);
				widget = label;
			}else{
				LabelledEditBox editBox = new LabelledEditBox(context, 
															  item.getLabel(), 
															  defaultValue);
				widget = editBox;
			}
			
			
		} else if ("DIGITS".equals(item.getType())
				|| "NUMERIC".equals(item.getType())) {
			boolean noDefault = PageItemProcessor.getBooleanAttribute(item, "nodefault");
			if(noDefault && defaultValue == null){
				defaultValue = "";
			}
			LabelledEditBox editBox = new LabelledEditBox(context,
					item.getLabel(), 
					defaultValue != null ? defaultValue : "0");
			editBox.setKeyListener(new DigitsKeyListener());
			widget = editBox;
		} else if ("DATETIME".equals(item.getType())) {
			final LabelledDatePicker datePicker = new LabelledDatePicker(
					context, item.getLabel(),
					defaultValue != null ? defaultValue : "");
			widget = datePicker;
		} else if ("YESNO".equals(item.getType())) {
			
			
			LabelledCheckBox checkBox = new LabelledCheckBox(
					context,
					item.getLabel(),
					defaultValue != null && (defaultValue.equals("true") || defaultValue.equals("Y")) 
							? true
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
			if(!multiSelect){
				spinnerArray.add(new NameValue(context.getResources().getString(R.string.spinner_none_selected),null)); //add a blank entry to avoid the first item being pre-selected.
			}

			ArrayList<String> dataItemValues = new ArrayList<String>();
			if(defaultValue != null){
				if(defaultValue.contains(",")){
					//multiselect value
					dataItemValues.addAll(Arrays.asList(defaultValue.split("[,]")));
				} else {
					dataItemValues.add(defaultValue);
				}
			}
			
			List<NameValue> selected = new ArrayList<NameValue>();
			List<LabelledValue> values = item.getValues();
			for(LabelledValue value: values){
				NameValue nameValue = new NameValue(value.getLabel(), value.getValue());
				if(dataItemValues.contains(value.getValue())){
					selected.add(nameValue);
				}
				spinnerArray.add(nameValue);
			}
			spinner.setItems(spinnerArray);
			for(NameValue selectedNameValue: selected){
				Log.d(TAG, "Setting selected value: " + selectedNameValue);
				spinner.setSelected(selectedNameValue);
			}
			widget = spinner;
		} else {
			TextView errorLabel = new TextView(context);
			errorLabel.setText("Unknown item: '" + item.getType() + "'");
			widget = errorLabel;
		}

		
		
		return new WidgetWrapper(widget, required, condition, readonly, hidden);
	}
}
