package uk.co.vurt.taskhelper.ui;

import uk.co.vurt.taskhelper.domain.definition.PageItem;
import uk.co.vurt.taskhelper.domain.job.DataItem;
import uk.co.vurt.taskhelper.ui.widget.LabelledDatePicker;
import uk.co.vurt.taskhelper.ui.widget.LabelledEditBox;
import android.content.Context;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.widget.TextView;

public class WidgetFactory {

	public static View createWidget(Context context, PageItem item, DataItem dataItem){

		View widget = null;
		
		//create new widget and add it to the map
		if("LABEL".equals(item.getType())){
			TextView label = new TextView(context);
			label.setText(item.getLabel());
			widget = label;
		}else if("TEXT".equals(item.getType())){
			LabelledEditBox editBox = new LabelledEditBox(context, item.getLabel(), dataItem != null ? dataItem.getValue() : item.getValue());
			widget = editBox;
		}else if("DIGITS".equals(item.getType())){
			LabelledEditBox editBox = new LabelledEditBox(context, item.getLabel(), dataItem != null ? dataItem.getValue() :  item.getValue());
			editBox.setKeyListener(new DigitsKeyListener());
			widget = editBox;
		}else if("DATETIME".equals(item.getType())){
			final LabelledDatePicker datePicker = new LabelledDatePicker(context, item.getLabel(), dataItem != null ? dataItem.getValue() :  item.getValue());
//			datePicker.setOnClickListener(new View.OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					showDatePickerDialog(datePicker);
//				}
//			});
			widget = datePicker;
		} else {
			TextView errorLabel = new TextView(context);
			errorLabel.setText("Unknown item: '" + item.getType() + "'");
			widget = errorLabel;
		}
		
		return widget;
	}
}
