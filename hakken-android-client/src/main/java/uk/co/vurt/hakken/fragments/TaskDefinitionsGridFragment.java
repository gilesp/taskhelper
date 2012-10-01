package uk.co.vurt.hakken.fragments;

import uk.co.vurt.hakken.R;
import uk.co.vurt.hakken.providers.Task;
import android.app.Activity;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

/**
 * Create a version of the TaskDefinitionsListFragment that uses a GridView
 * 
 * For the time being just use the same image for all task definitions, at a
 * later date we'll include appropriate images int eh data being synched from
 * the server.
 * 
 * Useful information about GridViews:
 * 
 * http://developer.android.com/guide/topics/ui/layout/gridview.html
 * 
 * http://stackoverflow.com/questions/10285634/how-to-gridview-inside-a-fragment
 * 
 * http://developer.android.com/design/building-blocks/grid-lists.html
 * 
 * @author giles
 * 
 */
public class TaskDefinitionsGridFragment extends Fragment  implements
LoaderManager.LoaderCallbacks<Cursor> {

	private GridView taskGrid;
	private SimpleCursorAdapter adapter;
	private OnTaskDefintionSelectedListener listener;

	private final static int TASK_LOADER = 0;
	private static final String TAG = "TaskDefinitionsGridFragment";

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		getLoaderManager().initLoader(1, null, this);
		
		taskGrid = (GridView) getView().findViewById(R.id.taskGrid);

		adapter = new SimpleCursorAdapter(getActivity(),
				R.layout.selecttask_grid_item, null,
				new String[] { Task.Definitions.NAME },
				new int[] { R.id.gridview_entry_name },
				CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
		taskGrid.setAdapter(adapter);
		
		taskGrid.setOnItemClickListener(taskClickListener);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_task_grid, container, false);
	}
	
	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		Log.d(TAG, "Attempting to create cursor loader");
		return new CursorLoader(getActivity(), Task.Definitions.CONTENT_URI,
				Task.Definitions.ALL, null, null, Task.Definitions.DEFAULT_SORT_ORDER);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		Log.d(TAG, "onLoadFinished called: " + data.getCount());
		adapter.swapCursor(data);
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		Log.d(TAG, "onLoaderReset called: ");
		adapter.swapCursor(null);
		adapter.notifyDataSetInvalidated();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			listener = (OnTaskDefintionSelectedListener) activity;
		} catch (ClassCastException cce) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnTaskDefintionSelectedListener");
		}
	}
	
	public interface OnTaskDefintionSelectedListener {
		public void onTaskDefintionSelected(Uri jobUri);
	}
	
	private OnItemClickListener taskClickListener = new OnItemClickListener(){
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Uri jobUri = ContentUris
					.withAppendedId(Task.Definitions.CONTENT_URI, id);
			listener.onTaskDefintionSelected(jobUri);
		}
	};
}
