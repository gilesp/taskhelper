package uk.co.vurt.hakken.ui.tabs.impl;

import uk.co.vurt.hakken.ui.tabs.CompatTab;
import uk.co.vurt.hakken.ui.tabs.CompatTabListener;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

@TargetApi(11)
public class CompatTabHoneycomb extends CompatTab implements ActionBar.TabListener{

	ActionBar.Tab tab;
	CompatTabListener callback;
	Fragment fragment;
	
	public CompatTabHoneycomb(FragmentActivity activity, String tag){
		super(activity, tag);
		tab = activity.getActionBar().newTab();
	}
	
	@Override
	public CompatTab setText(int resId) {
		tab.setText(resId);
		return this;
	}

	@Override
	public CompatTab setIcon(int resId) {
		tab.setIcon(resId);
		return this;
	}

	@Override
	public CompatTab setFragment(Fragment fragment) {
		this.fragment = fragment;
		return this;
	}

	@Override
	public CharSequence getText() {
		return tab.getText();
	}

	@Override
	public Drawable getIcon() {
		return tab.getIcon();
	}

	@Override
	public Fragment getFragment() {
		return fragment;
	}

	@Override
	public CompatTab setTabListener(CompatTabListener callback) {
		this.callback = callback;
		tab.setTabListener(this);
		return this;
	}

	@Override
	public CompatTabListener getCallback() {
		return callback;
	}

	public void onTabReselected(Tab tab, android.app.FragmentTransaction f) {
        FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
        ft.disallowAddToBackStack();
        callback.onTabReselected(this, ft);
        ft.commit();
    }

    @Override
    public void onTabSelected(Tab tab, android.app.FragmentTransaction f) {
        FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
        ft.disallowAddToBackStack();
        callback.onTabSelected(this, ft);
        ft.commit();
    }

    @Override
    public void onTabUnselected(Tab arg0, android.app.FragmentTransaction f) {
        FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
        ft.disallowAddToBackStack();
        callback.onTabUnselected(this, ft);
        ft.commit();
    }

	@Override
	public Object getTab() {
		return tab;
	}
}
