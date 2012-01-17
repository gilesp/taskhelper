package uk.co.vurt.taskhelper.domain.job;


import java.text.SimpleDateFormat;
import java.util.Date;

import uk.co.vurt.taskhelper.R;
import uk.co.vurt.taskhelper.providers.Job;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class JobDomainAdapter extends SimpleCursorAdapter {

	private Cursor cursor;
	LayoutInflater inflater;
	
	public JobDomainAdapter(Context context, int layout, Cursor cursor,
			String[] from, int[] to) {
		super(context, layout, cursor, from, to);
		this.cursor = cursor;
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		
		if(convertView == null){
			convertView = inflater.inflate(R.layout.selectjob_list_item, null);
			holder = new ViewHolder();
			holder.header = (TextView) convertView.findViewById(R.id.joblist_section_header);
			holder.name = (TextView) convertView.findViewById(R.id.joblist_entry_name);
			holder.duedate = (TextView) convertView.findViewById(R.id.joblist_entry_duedate);
			holder.status = (ImageView) convertView.findViewById(R.id.joblist_entry_completed);
			
			convertView.setTag(holder);
			
		} else {
			holder = (ViewHolder)convertView.getTag();
		}
		
		cursor.moveToPosition(position);
		String group = cursor.getString(cursor.getColumnIndex(Job.Definitions.GROUP)); //looking up column positions like this is porbably jsut as inefficient as when you do it with JDBC
		String name = cursor.getString(cursor.getColumnIndex(Job.Definitions.NAME));
		String status = cursor.getString(cursor.getColumnIndex(Job.Definitions.STATUS));
		long duedate = cursor.getLong(cursor.getColumnIndex(Job.Definitions.GROUP));
		
		String previousGroup = null;
		
		//find out what the previous item's(if there was one) group is
		if(cursor.getPosition() > 0 && cursor.moveToPrevious()){
			previousGroup = cursor.getString(cursor.getColumnIndex(Job.Definitions.GROUP)); //compounding one inefficiency with another...
			cursor.moveToNext();
		}
		

		if(group.equals(previousGroup)){
			//if the groups match, hide the header
			holder.header.setVisibility(View.GONE);
		} else {
			//show the header
			holder.header.setText(group);
			holder.header.setVisibility(View.VISIBLE);
		}
		
		holder.name.setText(name);
		
		//format the date for display
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
		holder.duedate.setText("Due: " + sdf.format(new Date(duedate)));
		
		//set the appropriate status indicator
		if("COMPLETED".equals(status)){
			holder.status.setImageResource(R.drawable.ic_completed_star);
		}else{
			holder.status.setImageResource(R.drawable.ic_uncompleted_star);
		}

		return convertView;
	}

	/**
	 * This needs to reflect the xml used to define the list item view.
	 * @author giles.paterson
	 *
	 */
	static class ViewHolder {
		TextView header;
		TextView name;
		TextView duedate;
		ImageView status;
	}
}
