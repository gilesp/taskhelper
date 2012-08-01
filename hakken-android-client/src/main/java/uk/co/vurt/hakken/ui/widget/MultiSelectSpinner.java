package uk.co.vurt.hakken.ui.widget;

import java.util.ArrayList;
import java.util.List;

import uk.co.vurt.hakken.R;
import uk.co.vurt.hakken.domain.NameValue;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.Spinner;

public class MultiSelectSpinner extends Spinner implements
		OnMultiChoiceClickListener, OnCancelListener {

	private static final String TAG = "MultiSelectSpinner";
	
	private List<NameValue> items;
	private boolean selected[];
	private String defaultText;
	
	public MultiSelectSpinner(Context context) {
		super(context);
	}

	
	public MultiSelectSpinner(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}


	public MultiSelectSpinner(Context context, AttributeSet attrs) {
		super(context, attrs);
	}


	public boolean performClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        if(items != null && selected != null){
        	CharSequence[] labels = new CharSequence[items.size()];
        	for(int i = 0; i < items.size(); i++){
        		labels[i] = items.get(i).getName();
        	}
        	builder.setMultiChoiceItems(labels, selected, this);
        }
        builder.setPositiveButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        builder.setOnCancelListener(this);
        builder.show();
        return true;
    }
	
	public void setItems(List<NameValue> items, String allText) {
        this.items = items;
        this.defaultText = allText;

        // none selected by default
        selected = new boolean[items.size()];
        for (int i = 0; i < selected.length; i++){
            selected[i] = false;
        }
        
        // all text on the spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                R.layout.spinner_item, new String[] { allText });
        setAdapter(adapter);
    }

	public List<NameValue> getItems(){
		return items;
	}
	
	@Override
	public void onCancel(DialogInterface arg0) {
//		// refresh text on spinner
//        StringBuffer spinnerBuffer = new StringBuffer();
//        boolean someUnselected = false;
//        if(items != null){
//	        for (int i = 0; i < items.size(); i++) {
//	            if (selected[i] == true) {
//	                spinnerBuffer.append(items.get(i));
//	                spinnerBuffer.append(", ");
//	            } else {
//	                someUnselected = true;
//	            }
//	        }
//        }
//        String spinnerText;
//        if (someUnselected) {
//            spinnerText = spinnerBuffer.toString();
//            if (spinnerText.length() > 2){
//                spinnerText = spinnerText.substring(0, spinnerText.length() - 2);
//            }
//        } else {
//            spinnerText = defaultText;
//        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                R.layout.spinner_item,
                new String[] { getSpinnerText() });
        setAdapter(adapter);
	}

	public String getSpinnerText(){
		StringBuffer spinnerBuffer = new StringBuffer();
        boolean someUnselected = false;
        if(items != null){
	        for (int i = 0; i < items.size(); i++) {
	            if (selected[i] == true) {
	                spinnerBuffer.append(items.get(i));
	                spinnerBuffer.append(", ");
	            } else {
	                someUnselected = true;
	            }
	        }
        }
        String spinnerText;
        if (someUnselected) {
            spinnerText = spinnerBuffer.toString();
            if (spinnerText.length() > 2){
                spinnerText = spinnerText.substring(0, spinnerText.length() - 2);
            }
        } else {
            spinnerText = defaultText;
        }
        
        return spinnerText;
	}
	
	@Override
	public void onClick(DialogInterface dialog, int which, boolean isChecked) {
		if(isChecked){
			selected[which] = true;
		}else{
			selected[which] = false;
		}
	}
	
	public List<NameValue> getSelectedItems(){
		ArrayList<NameValue> selectedItems = new ArrayList<NameValue>();
		for(int i = 0; i < selected.length; i++){
			if(selected[i]){
				selectedItems.add(items.get(i));
			}
		}
		return selectedItems;
	}
	
	public void setSelected(int itemPosition){
		if(itemPosition > 0 && itemPosition <= selected.length){
			
			Log.d(TAG, "Attempting to set item " + itemPosition + " as selected");
			selected[itemPosition] = true;
//			((CheckedTextView)getItemAtPosition(itemPosition)).setChecked(true);
//			((CheckedTextView)((ArrayAdapter)getAdapter()).getItem(itemPosition)).setChecked(true);
		} else {
			throw new RuntimeException("Invalid item position.");
		}
	}
	
//	public void setSelected(boolean[] selections){
//		if(selections.length == selected.length){
//			for(int i = 0; i < selected.length; i++){
//				if(selections[i]){
//					selected[i] = selections[i];
//					CheckedTextView checkedTextView = (CheckedTextView)((ArrayAdapter)getAdapter()).getItem(i);
//					checkedTextView.setChecked(selections[i]);
//				}
//			}
//		} else {
//			throw new RuntimeException("Incorrect number of selections provided.");
//		}
//	}
}
