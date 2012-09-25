package uk.co.vurt.hakken.activities;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;

import uk.co.vurt.hakken.R;
import uk.co.vurt.hakken.domain.task.TaskDefinition;
import uk.co.vurt.hakken.fragments.JobListFragment;
import uk.co.vurt.hakken.fragments.JobListFragment.OnJobSelectedListener;
import uk.co.vurt.hakken.fragments.TaskDefinitionsListFragment;
import uk.co.vurt.hakken.processor.TaskProcessor;
import uk.co.vurt.hakken.providers.Job;
import uk.co.vurt.hakken.fragments.TaskDefinitionsListFragment.OnTaskDefintionSelectedListener;
import uk.co.vurt.hakken.providers.TaskProvider;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.SQLException;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;
import android.widget.Toast;

public class SelectorActivity extends FragmentActivity implements OnJobSelectedListener, OnTaskDefintionSelectedListener{
	
	private final static String TAG = "SelectorActivity";
	
	TabHost tabHost;
	TabManager tabManager;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_tabs);
		tabHost = (TabHost)findViewById(android.R.id.tabhost);
		tabHost.setup();
		
		tabManager = new TabManager(this, tabHost, R.id.realtabcontent);
		
		tabManager.addTab(tabHost.newTabSpec("jobs").setIndicator("Jobs"),
				JobListFragment.class, null);
		
		tabManager.addTab(tabHost.newTabSpec("definitions").setIndicator("Definitions"),
				TaskDefinitionsListFragment.class, null);
	}
	
	@Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("tab", tabHost.getCurrentTabTag());
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
	
	/**
     * This is a helper class that implements a generic mechanism for
     * associating fragments with the tabs in a tab host.  It relies on a
     * trick.  Normally a tab host has a simple API for supplying a View or
     * Intent that each tab will show.  This is not sufficient for switching
     * between fragments.  So instead we make the content part of the tab host
     * 0dp high (it is not shown) and the TabManager supplies its own dummy
     * view to show as the tab content.  It listens to changes in tabs, and takes
     * care of switch to the correct fragment shown in a separate content area
     * whenever the selected tab changes.
     */
    public static class TabManager implements TabHost.OnTabChangeListener {
        private final FragmentActivity mActivity;
        private final TabHost mTabHost;
        private final int mContainerId;
        private final HashMap<String, TabInfo> mTabs = new HashMap<String, TabInfo>();
        TabInfo mLastTab;

        static final class TabInfo {
            private final String tag;
            private final Class<?> clss;
            private final Bundle args;
            private Fragment fragment;

            TabInfo(String _tag, Class<?> _class, Bundle _args) {
                tag = _tag;
                clss = _class;
                args = _args;
            }
        }

        static class DummyTabFactory implements TabHost.TabContentFactory {
            private final Context mContext;

            public DummyTabFactory(Context context) {
                mContext = context;
            }

            @Override
            public View createTabContent(String tag) {
                View v = new View(mContext);
                v.setMinimumWidth(0);
                v.setMinimumHeight(0);
                return v;
            }
        }

        public TabManager(FragmentActivity activity, TabHost tabHost, int containerId) {
            mActivity = activity;
            mTabHost = tabHost;
            mContainerId = containerId;
            mTabHost.setOnTabChangedListener(this);
        }

        public void addTab(TabHost.TabSpec tabSpec, Class<?> clss, Bundle args) {
            tabSpec.setContent(new DummyTabFactory(mActivity));
            String tag = tabSpec.getTag();

            TabInfo info = new TabInfo(tag, clss, args);

            // Check to see if we already have a fragment for this tab, probably
            // from a previously saved state.  If so, deactivate it, because our
            // initial state is that a tab isn't shown.
            info.fragment = mActivity.getSupportFragmentManager().findFragmentByTag(tag);
            if (info.fragment != null && !info.fragment.isDetached()) {
                FragmentTransaction ft = mActivity.getSupportFragmentManager().beginTransaction();
                ft.detach(info.fragment);
                ft.commit();
            }

            mTabs.put(tag, info);
            mTabHost.addTab(tabSpec);
        }

        @Override
        public void onTabChanged(String tabId) {
            TabInfo newTab = mTabs.get(tabId);
            if (mLastTab != newTab) {
                FragmentTransaction ft = mActivity.getSupportFragmentManager().beginTransaction();
                if (mLastTab != null) {
                    if (mLastTab.fragment != null) {
                        ft.detach(mLastTab.fragment);
                    }
                }
                if (newTab != null) {
                    if (newTab.fragment == null) {
                        newTab.fragment = Fragment.instantiate(mActivity,
                                newTab.clss.getName(), newTab.args);
                        ft.add(mContainerId, newTab.fragment, newTab.tag);
                    } else {
                        ft.attach(newTab.fragment);
                    }
                }

                mLastTab = newTab;
                ft.commit();
                mActivity.getSupportFragmentManager().executePendingTransactions();
            }
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
//			values.put(Job.Definitions._ID, jobId);
			values.put(Job.Definitions.NAME, jobName);
			values.put(Job.Definitions.CREATED, now);
			values.put(Job.Definitions.DUE, now);
			values.put(Job.Definitions.TASK_DEFINITION_ID, definition.getId());
			values.put(Job.Definitions.STATUS, "AWAITING");
			
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
