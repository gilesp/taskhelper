package uk.co.vurt.hakken.fragments;

import java.text.SimpleDateFormat;
import java.util.Date;

import uk.co.vurt.hakken.providers.Job;
import uk.co.vurt.hakken.R;
import android.app.Activity;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class JobListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

	private OnJobSelectedListener listener;
	private boolean isTablet = false;
	private int currentSelectedItemIndex = -1;
	
	private final static int TASK_LOADER = 1;
	/**
	 * The columns from the data base that we are interested in
	 */
	private static final String[] PROJECTION = new String[] {
		Job.Definitions._ID, //0
		Job.Definitions.NAME, //1
		Job.Definitions.DUE, //2
		Job.Definitions.STATUS //3
	};
	
	private SimpleCursorAdapter adapter;
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getLoaderManager().initLoader(TASK_LOADER, null, this);
		
		adapter = new SimpleCursorAdapter(getActivity().getApplicationContext(), R.layout.selectjob_list_item, null, new String[] { Job.Definitions.NAME, Job.Definitions.DUE, Job.Definitions.STATUS }, 
				new int[] { R.id.joblist_entry_name, R.id.joblist_entry_duedate, R.id.joblist_entry_completed}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
		
		adapter.setViewBinder(new ViewBinder(){

			@Override
			public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
				if(columnIndex == 2){
					long dueDate = cursor.getLong(2);
					TextView textView = (TextView)view;
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
					textView.setText("Due: " + sdf.format(new Date(dueDate)));
					return true;
				} else if(columnIndex == 3){
					String status = cursor.getString(3);
					ImageView image = (ImageView)view;
					if("COMPLETED".equals(status)){
						image.setImageResource(R.drawable.ic_completed_star);
					}else{
						image.setImageResource(R.drawable.ic_uncompleted_star);
					}
					return true;
				}
				return false;
			}
        	
        });
		setListAdapter(adapter);
	}



	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		return new CursorLoader(getActivity(), Job.Definitions.CONTENT_URI, PROJECTION, null, null, null);
	}


	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		adapter.swapCursor(cursor);
	}


	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		adapter.swapCursor(null);
	}

	@Override
	public void onListItemClick(ListView list, View view, int position, long id){
		Uri jobUri = ContentUris.withAppendedId(Job.Definitions.CONTENT_URI, id);
		listener.onJobSelected(jobUri);
	}
	
	@Override
	public void onAttach(Activity activity){
		super.onAttach(activity);
		try{
			listener = (OnJobSelectedListener)activity;
		}catch (ClassCastException cce){
			throw new ClassCastException(activity.toString() + " must implement OnJobSelectedListener");
		}
	}
	
	public interface OnJobSelectedListener {
		public void onJobSelected(Uri jobUri);
	}
}
