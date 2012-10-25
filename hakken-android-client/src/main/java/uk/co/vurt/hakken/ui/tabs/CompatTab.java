package uk.co.vurt.hakken.ui.tabs;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

public abstract class CompatTab {

	protected final FragmentActivity activity;
	final String tag;
	
	protected CompatTab(FragmentActivity activity, String tag){
		this.activity = activity;
		this.tag = tag;
	}
	
	public abstract CompatTab setText(int resId);
	public abstract CompatTab setIcon(int resId);
	public abstract CompatTab setTabListener(CompatTabListener callback);
	public abstract CompatTab setFragment(Fragment fragment);
	public abstract CharSequence getText();
	public abstract Drawable getIcon();
	public abstract CompatTabListener getCallback();
	public abstract Fragment getFragment();
	public abstract Object getTab();
	
	public String getTag(){
		return tag;
	}
}
