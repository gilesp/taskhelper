package uk.co.vurt.hakken.ui.widget;

import java.io.Serializable;
import java.util.List;

import uk.co.vurt.hakken.domain.NameValue;
import android.content.Context;
import android.widget.ArrayAdapter;

public class NameValueAdapter extends ArrayAdapter<NameValue> implements Serializable{

	private List<NameValue> values;
	
	public NameValueAdapter(Context context, int textViewResourceId,
			List<NameValue> values) {
		super(context, textViewResourceId, values);
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
