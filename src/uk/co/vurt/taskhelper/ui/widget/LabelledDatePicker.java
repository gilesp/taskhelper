package uk.co.vurt.taskhelper.ui.widget;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LabelledDatePicker extends LinearLayout {

	private TextView label;
	private EditText textBox;
	private Button pickDateButton;

	private int mYear;
	private int mMonth;
	private int mDay;
	
	static final int DATE_DIALOG_ID = 0;
	
	// the callback received when the user "sets" the date in the dialog
    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year, 
                                      int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                    updateDisplay();
                }
            };
            
	public LabelledDatePicker(Context context, String labelText, String initialValue) {
		super(context);
		label = new TextView(context);
		label.setText(labelText);
		textBox = new EditText(context);
		textBox.setEnabled(false);
		if(initialValue != null){
			textBox.setText(initialValue);
		}
		textBox.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		pickDateButton = new Button(context);
		pickDateButton.setText("Date");
		pickDateButton.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));


		// add a click listener to the button
//        pickDateButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                showDialog(DATE_DIALOG_ID);
//            }
//        });

        
		this.addView(label);
		this.addView(textBox);
		this.addView(pickDateButton);
	}
	
	private void updateDisplay() {
        textBox.setText(
            new StringBuilder()
                    .append(mDay).append("-")
                    // Month is 0 based so add 1
                    .append(mMonth + 1).append("-")
                    .append(mYear).append(" "));
    }
	
//	protected Dialog onCreateDialog(int id) {
//	    switch (id) {
//	    case DATE_DIALOG_ID:
//	        return new DatePickerDialog(this,
//	                    mDateSetListener,
//	                    mYear, mMonth, mDay);
//	    }
//	    return null;
//	}
	
	public String getValue(){
		return textBox.getText().toString();
	}
	
	public void setValue(String value){
		textBox.setText(value);
	}
	
	public void setLabel(String labelText){
		label.setText(labelText);
	}
}
