package uk.co.vurt.hakken.domain.task;


import uk.co.vurt.hakken.providers.Task;
import uk.co.vurt.hakken.R;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class TaskDomainAdapter extends SimpleCursorAdapter {

	private Cursor cursor;
	LayoutInflater inflater;
	
	public TaskDomainAdapter(Context context, int layout, Cursor cursor,
			String[] from, int[] to, int flag) {
		super(context, layout, cursor, from, to, flag);
//		this.cursor = cursor;
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		
		if(convertView == null){
			convertView = inflater.inflate(R.layout.selecttask_list_item, null);
			holder = new ViewHolder();
			holder.name = (TextView) convertView.findViewById(R.id.tasklist_entry_name);
			
			convertView.setTag(holder);
			
		} else {
			holder = (ViewHolder)convertView.getTag();
		}
		
		cursor.moveToPosition(position);
		String name = cursor.getString(cursor.getColumnIndex(Task.Definitions.NAME));
					
		holder.name.setText(name);				

		return convertView;
	}

	/**
	 * This needs to reflect the xml used to define the list item view.
	 * @author giles.paterson
	 *
	 */
	static class ViewHolder {
		TextView name;
	}
}
