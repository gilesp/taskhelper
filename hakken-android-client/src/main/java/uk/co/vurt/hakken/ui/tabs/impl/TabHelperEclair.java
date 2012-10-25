package uk.co.vurt.hakken.ui.tabs.impl;

import java.util.HashMap;

import uk.co.vurt.hakken.ui.tabs.CompatTab;
import uk.co.vurt.hakken.ui.tabs.CompatTabListener;
import uk.co.vurt.hakken.ui.tabs.TabHelper;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class TabHelperEclair extends TabHelper implements TabHost.OnTabChangeListener{

	private final HashMap<String, CompatTab> tabs = new HashMap<String, CompatTab>();
    private TabHost tabHost;
    CompatTabListener callback;
    CompatTab lastTab;

    public TabHelperEclair(FragmentActivity activity){
    	super(activity);
    	this.activity = activity;
    }
    
    protected void setUp() {
    	if(tabHost == null){
    		tabHost = (TabHost)activity.findViewById(android.R.id.tabhost);
    		tabHost.setup();
    		tabHost.setOnTabChangedListener(this);
    	}
    }
    
	@Override
	public void addTab(CompatTab tab) {
		String tag = tab.getTag();
		TabSpec spec;
		
		if(tab.getIcon() != null){
			spec = tabHost.newTabSpec(tag).setIndicator(tab.getText(), tab.getIcon());
		} else {
			spec = tabHost.newTabSpec(tag).setIndicator(tab.getText());
		}
		spec.setContent(new DummyTabFactory(activity));
		
		Fragment fragment = activity.getSupportFragmentManager().findFragmentByTag(tag);
		tab.setFragment(fragment);
		
		if(fragment != null && !fragment.isDetached()){
			FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
			ft.detach(fragment);
			ft.commit();
		}

		tabs.put(tag, tab);
		tabHost.addTab(spec);
	}

	public void onTabChanged(String tabId){
		CompatTab newTab = tabs.get(tabId);
		FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
		
		if(lastTab != newTab){
			if(lastTab != null){
				if(lastTab.getFragment() != null){
					lastTab.getCallback().onTabUnselected(lastTab,  ft);
				}
			}
			if(newTab != null){
				newTab.getCallback().onTabSelected(newTab, ft);
			}
			lastTab = newTab;
		} else {
			newTab.getCallback().onTabReselected(newTab,  ft);
		}
		ft.commit();
		activity.getSupportFragmentManager().executePendingTransactions();
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putString("tab",  tabHost.getCurrentTabTag());
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		if(savedInstanceState != null){
			tabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
		}
	}

	static class DummyTabFactory implements TabHost.TabContentFactory {
		private final Context context;
		
		public DummyTabFactory(Context context) {
			this.context = context;
		}
		
		public View createTabContent(String tag) {
			View view = new View(context);
			view.setMinimumWidth(0);
			view.setMinimumHeight(0);
			return view;
		}
	}
}
