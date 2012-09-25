package uk.co.vurt.hakken.fragments;

import uk.co.vurt.hakken.R;
import uk.co.vurt.hakken.domain.job.JobDomainAdapter;
import uk.co.vurt.hakken.domain.task.TaskDomainAdapter;
import uk.co.vurt.hakken.fragments.JobListFragment.OnJobSelectedListener;
import uk.co.vurt.hakken.providers.Job;
import uk.co.vurt.hakken.providers.Task;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

//TODO Kash & Martina convert the TaskList activity to this fragment. See JobList and 
//JobListFragment for an example of how this is done.
public class TaskDefinitionsListFragment extends ListFragment implements
		LoaderManager.LoaderCallbacks<Cursor> {
	
	private static final String TAG = "TaskDefinitonsListFragment";

	private OnTaskDefintionSelectedListener listener;

	private final static int TASK_LOADER = 0;

	private TaskDomainAdapter adapter;

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setRetainInstance(true);
		getLoaderManager().initLoader(TASK_LOADER, null, this);
		// Create an empty adapter we will use to display the loaded data.
		// We pass null for the cursor, then update it in onLoadFinished()
		// Used to map task definition entries from the database to views
		adapter = new TaskDomainAdapter(super.getActivity().getApplicationContext(),
				R.layout.selecttask_list_item, null, new String[] {
						Task.Definitions.NAME}, new int[] {
						R.id.tasklist_entry_name},
						CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
		setListAdapter(adapter);
				
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.fragment_task_list, null);
		return root;
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		Log.d(TAG, "Attempting to create cursor loader");
		return new CursorLoader(getActivity(), Task.Definitions.CONTENT_URI,
				Task.Definitions.ALL, null, null, Task.Definitions.DEFAULT_SORT_ORDER);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		Log.d(TAG, "onLoadFinished called: " + cursor);
		adapter.swapCursor(cursor);
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		Log.d(TAG, "onLoaderReset called.");
		adapter.swapCursor(null);
		adapter.notifyDataSetInvalidated();
	}

	@Override
	public void onListItemClick(ListView list, View view, int position, long id) {
		Uri jobUri = ContentUris
				.withAppendedId(Task.Definitions.CONTENT_URI, id);
		listener.onTaskDefintionSelected(jobUri);
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

}