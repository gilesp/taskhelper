package uk.co.vurt.hakken.activities;

import java.text.DateFormat;
import java.util.Date;

import uk.co.vurt.hakken.R;
import uk.co.vurt.hakken.domain.task.TaskDefinition;
import uk.co.vurt.hakken.fragments.JobListFragment;
import uk.co.vurt.hakken.fragments.JobListFragment.OnJobSelectedListener;
import uk.co.vurt.hakken.fragments.TaskDefinitionsGridFragment;
import uk.co.vurt.hakken.fragments.TaskDefinitionsGridFragment.OnTaskDefintionSelectedListener;
import uk.co.vurt.hakken.processor.TaskProcessor;
import uk.co.vurt.hakken.providers.Job;
import uk.co.vurt.hakken.providers.TaskProvider;
import uk.co.vurt.hakken.ui.tabs.CompatTab;
import uk.co.vurt.hakken.ui.tabs.CompatTabListener;
import uk.co.vurt.hakken.ui.tabs.TabCompatActivity;
import uk.co.vurt.hakken.ui.tabs.TabHelper;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.SQLException;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class SelectorActivity extends TabCompatActivity implements OnJobSelectedListener, OnTaskDefintionSelectedListener{
	
	private final static String TAG = "SelectorActivity";
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selector_activity);
		
		TabHelper tabHelper = getTabHelper();
		
		CompatTab jobsTab = tabHelper.newTab("jobs")
				.setText(R.string.tab_jobs)
				.setIcon(R.drawable.job_tab)
				.setTabListener(new InstantiatingTabListener(this, JobListFragment.class));
		tabHelper.addTab(jobsTab);
		
		CompatTab definitionsTab = tabHelper.newTab("definitions")
				.setText(R.string.tab_task_definitions)
				.setIcon(R.drawable.task_tab)
				.setTabListener(new InstantiatingTabListener(this, TaskDefinitionsGridFragment.class));
		tabHelper.addTab(definitionsTab);
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
                new ComponentName(this, SelectorActivity.class), null, intent, 0, null);

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
	
	public static class InstantiatingTabListener implements CompatTabListener {
		
		private final TabCompatActivity activity;
		private final Class clazz;
		
		public InstantiatingTabListener(TabCompatActivity activity, Class<? extends Fragment> clazz){
			this.activity = activity;
			this.clazz = clazz;
		}
		
		public void onTabSelected(CompatTab tab, FragmentTransaction ft){
			Fragment fragment = tab.getFragment();
			if(fragment == null){
				fragment = Fragment.instantiate(activity, clazz.getName());
				tab.setFragment(fragment);
				ft.add(android.R.id.tabcontent, fragment, tab.getTag());
			} else {
				ft.attach(fragment);
			}
		}
		
		public void onTabUnselected(CompatTab tab, FragmentTransaction ft){
			Fragment fragment = tab.getFragment();
			if(fragment != null){
				ft.detach(fragment);
			}
		}
		public void onTabReselected(CompatTab tab, FragmentTransaction ft){
			//tab already selected, so do nothing
		}
	}

	@Override
	public void onJobSelected(Uri jobUri) {
        String action = getIntent().getAction();
        if (Intent.ACTION_PICK.equals(action) || Intent.ACTION_GET_CONTENT.equals(action)) {
            // The caller is waiting for us to return a task definition selected by
            // the user.  They have clicked on one, so return it now.
            setResult(RESULT_OK, new Intent().setData(jobUri));
        } else {
            // Launch activity to view/edit the currently selected item
            startActivity(new Intent(Intent.ACTION_RUN, jobUri));
        }
	}

	@Override
	public void onTaskDefintionSelected(Uri definitionUri) {
		String action = getIntent().getAction();
		if (Intent.ACTION_PICK.equals(action) || Intent.ACTION_GET_CONTENT.equals(action)) {
            // The caller is waiting for us to return a task definition selected by
            // the user.  They have clicked on one, so return it now.
            setResult(RESULT_OK, new Intent().setData(definitionUri));
        } else {
        	//create a new job instance based on the task definition
        	TaskProcessor taskProcessor = new TaskProcessor(getContentResolver(), definitionUri);
        	TaskDefinition definition = taskProcessor.getTaskDefinition();

        	DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());

        	long now = System.currentTimeMillis();
        	String jobId = definition.getName() + " " + dateFormat.format(new Date(now));
        	String jobName = "Ad Hoc " + jobId;
        	
        	ContentValues values = new ContentValues();
			values.put(Job.Definitions.NAME, jobName);
			values.put(Job.Definitions.CREATED, now);
			values.put(Job.Definitions.DUE, now);
			values.put(Job.Definitions.TASK_DEFINITION_ID, definition.getId());
			values.put(Job.Definitions.STATUS, "AWAITING");
			values.put(Job.Definitions.ADHOC, true);
			
			try{
				Uri jobUri = getContentResolver().insert(Job.Definitions.CONTENT_URI, values);
				
	            // Launch activity to view/edit the newly created job
	            startActivity(new Intent(Intent.ACTION_RUN, jobUri));
			}catch(SQLException sqle){
				Toast.makeText( this, "Unable to create job instance:\n" + sqle.getMessage(), Toast.LENGTH_LONG).show();
			}
        }
	}

}
