package uk.co.vurt.hakken.ui.tabs.impl;

import uk.co.vurt.hakken.ui.tabs.CompatTab;
import uk.co.vurt.hakken.ui.tabs.CompatTabListener;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

public class CompatTabEclair extends CompatTab {

	private CompatTabListener callback;
    private CharSequence text;
    private Drawable icon;
    private Fragment fragment;
    
    public CompatTabEclair(FragmentActivity activity, String tag) {
        super(activity, tag);
    }
    
	@Override
	public CompatTab setText(int resId) {
		text = activity.getResources().getText(resId);
		return this;
	}

	@Override
	public CompatTab setIcon(int resId) {
		icon = activity.getResources().getDrawable(resId);
		return this;
	}

	@Override
	public CompatTab setTabListener(CompatTabListener callback) {
		this.callback = callback;
		return this;
	}

	@Override
	public CompatTab setFragment(Fragment fragment) {
		this.fragment = fragment;
		return this;
	}

	@Override
	public CharSequence getText() {
		return text;
	}

	@Override
	public Drawable getIcon() {
		return icon;
	}

	@Override
	public CompatTabListener getCallback() {
		return callback;
	}

	@Override
	public Fragment getFragment() {
		return fragment;
	}

	@Override
	public Object getTab() {
		return null;
	}

}
