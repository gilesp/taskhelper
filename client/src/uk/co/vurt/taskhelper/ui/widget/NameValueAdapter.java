package uk.co.vurt.taskhelper.ui.widget;

import java.util.List;

import uk.co.vurt.taskhelper.domain.NameValue;
import android.content.Context;
import android.widget.ArrayAdapter;

public class NameValueAdapter extends ArrayAdapter<NameValue> {

	private Context context;
	private List<NameValue> values;
	
	public NameValueAdapter(Context context, int textViewResourceId,
			List<NameValue> values) {
		super(context, textViewResourceId, values);
		this.context = context;
		this.values = values;
	}
	
	public int getCount(){
		return values.size();
	}
	
	public String getValue(int position){
		
		return values.get(position).getValue();
	}
	
	public NameValue getNameValue(int position){
		return values.get(position);
	}

}
