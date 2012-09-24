package uk.co.vurt.hakken.activities;

import uk.co.vurt.hakken.domain.task.TaskDomainAdapter;
import uk.co.vurt.hakken.providers.Task;
import uk.co.vurt.hakken.providers.TaskProvider;
import uk.co.vurt.hakken.R;
import android.app.ListActivity;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class TaskList extends ListActivity {
	private static final String TAG = "TaskList";
	
	/**
     * The columns we are interested in from the database
     */
    private static final String[] PROJECTION = new String[] {
            Task.Definitions._ID, // 0
            Task.Definitions.NAME, // 1            
    };
    
    /** The index of the name column */
    private static final int COLUMN_INDEX_NAME = 1;
    
    private SimpleCursorAdapter adapter;
    
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setDefaultKeyMode(DEFAULT_KEYS_SHORTCUT);
		
		// If no data was given in the intent (because we were started
        // as a MAIN activity), then use our default content provider.
        Intent intent = getIntent();
        if (intent.getData() == null) {
            intent.setData(Task.Definitions.CONTENT_URI);
        }
        
        // Inform the list we provide context menus for items
        getListView().setOnCreateContextMenuListener(this);
        
        // Perform a managed query. The Activity will handle closing and requerying the cursor
        // when needed.
        Cursor cursor = managedQuery(getIntent().getData(), PROJECTION, null, null, Task.Definitions.DEFAULT_SORT_ORDER);

        // Used to map task definition entries from the database to views
        adapter = new TaskDomainAdapter(this, R.layout.selecttask_list_item, cursor,
				new String[] { Task.Definitions.NAME }, 
				new int[] { R.id.tasklist_entry_name});
        
        setListAdapter(adapter);
	}
    
	public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.taskhelper_menu, menu);


        // Generate any additional actions that can be performed on the
        // overall list.  In a normal install, there are no additional
        // actions found here, but this allows other applications to extend
        // our menu with their own actions.
        Intent intent = new Intent(null, getIntent().getData());
        intent.addCategory(Intent.CATEGORY_ALTERNATIVE);
        menu.addIntentOptions(Menu.CATEGORY_ALTERNATIVE, 0, 0,
                new ComponentName(this, TaskList.class), null, intent, 0, null);

        return true;
    }
    
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
	    switch (item.getItemId()) {
	    	case R.id.synchronise:
	    		synchronise();
	    		return true;
	    	case R.id.preferences:
				startActivity(new Intent(this, PreferencesActivity.class));
	    		return true;
	    	case R.id.version:
	    		showVersionInfo();
	    		return true;
	    	default:
	    		return super.onOptionsItemSelected(item);
	    }
	}

	private void showVersionInfo(){
		PackageManager manager = this.getPackageManager();
		PackageInfo info;
		String message;
		try {
			info = manager.getPackageInfo(this.getPackageName(), 0);
			message = "PackageName = " + info.packageName + 
				     "\nVersionCode = " + info.versionCode + 
				     "\nVersionName = " + info.versionName;
		} catch (NameNotFoundException e) {
			message = "Unable to retrieve package info.";
		}
		Toast.makeText( this, message, Toast.LENGTH_LONG).show();

	}
	
	private void synchronise(){
		Log.d(TAG, "synchronise() called.");
		Bundle bundle = new Bundle();
		bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
		bundle.putBoolean(ContentResolver.SYNC_EXTRAS_FORCE, true);
		bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
		ContentResolver.requestSync(null, TaskProvider.AUTHORITY, bundle);
	}
	
	public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        final boolean haveItems = getListAdapter().getCount() > 0;

        // If there are any jobs in the list (which implies that one of
        // them is selected), then we need to generate the actions that
        // can be performed on the current selection.  This will be a combination
        // of our own specific actions along with any extensions that can be
        // found.
        if (haveItems) {
            // This is the selected item.
            Uri uri = ContentUris.withAppendedId(getIntent().getData(), getSelectedItemId());

            // Build menu...  always starts with the EDIT action...
            Intent[] specifics = new Intent[1];
            specifics[0] = new Intent(Intent.ACTION_EDIT, uri);
            MenuItem[] items = new MenuItem[1];

            // ... is followed by whatever other actions are available...
            Intent intent = new Intent(null, uri);
            intent.addCategory(Intent.CATEGORY_ALTERNATIVE);
            menu.addIntentOptions(Menu.CATEGORY_ALTERNATIVE, 0, 0, null, specifics, intent, 0,
                    items);

            // Give a shortcut to the edit action.
            if (items[0] != null) {
                items[0].setShortcut('1', 'e');
            }
        } else {
            menu.removeGroup(Menu.CATEGORY_ALTERNATIVE);
        }

        return true;
    }
	
	protected void onListItemClick(ListView l, View v, int position, long id) {
        Uri uri = ContentUris.withAppendedId(getIntent().getData(), id);
        
        String action = getIntent().getAction();
        if (Intent.ACTION_PICK.equals(action) || Intent.ACTION_GET_CONTENT.equals(action)) {
            // The caller is waiting for us to return a task definition selected by
            // the user.  The have clicked on one, so return it now.
            setResult(RESULT_OK, new Intent().setData(uri));
        } else {
            // Launch activity to view/edit the currently selected item
            startActivity(new Intent(Intent.ACTION_RUN, uri));
        }
    }

}
