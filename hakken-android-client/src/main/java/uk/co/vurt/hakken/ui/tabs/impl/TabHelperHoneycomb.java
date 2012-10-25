package uk.co.vurt.hakken.ui.tabs.impl;

import uk.co.vurt.hakken.ui.tabs.CompatTab;
import uk.co.vurt.hakken.ui.tabs.TabHelper;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

@TargetApi(11)
public class TabHelperHoneycomb extends TabHelper {
	ActionBar actionBar;

	public TabHelperHoneycomb(FragmentActivity activity) {
		super(activity);
	}

	protected void setUp() {
		if (actionBar == null) {
			actionBar = activity.getActionBar();
			actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		}
	}

	public void addTab(CompatTab tab) {
		String tag = tab.getTag();
		
		// Check to see if we already have a fragment for this tab, probably
        // from a previously saved state.  If so, deactivate it, because our
        // initial state is that a tab isn't shown.

        Fragment fragment = activity.getSupportFragmentManager().findFragmentByTag(tag);
        tab.setFragment(fragment);

        if (fragment != null && !fragment.isDetached()) {
            FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
            ft.detach(fragment);
            ft.commit();
        }

        if (tab.getCallback() == null) {
            throw new IllegalStateException("CompatTab must have a CompatTabListener");
        }

        // We know tab is a CompatTabHoneycomb instance, so its
        // native tab object is an ActionBar.Tab.
        actionBar.addTab((ActionBar.Tab) tab.getTab());
	}

	@Override
    protected void onSaveInstanceState(Bundle outState) {
        int position = actionBar.getSelectedTab().getPosition();
        outState.putInt("tab_position", position);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        int position = savedInstanceState.getInt("tab_position");
        actionBar.setSelectedNavigationItem(position);
    }
}
