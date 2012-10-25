package uk.co.vurt.hakken.ui.tabs;

import uk.co.vurt.hakken.ui.tabs.impl.CompatTabEclair;
import uk.co.vurt.hakken.ui.tabs.impl.CompatTabHoneycomb;
import uk.co.vurt.hakken.ui.tabs.impl.TabHelperEclair;
import uk.co.vurt.hakken.ui.tabs.impl.TabHelperHoneycomb;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public abstract class TabHelper {

	protected FragmentActivity activity;

	protected TabHelper(FragmentActivity activity) {
		this.activity = activity;
	}

	public static TabHelper createInstance(FragmentActivity activity) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			return new TabHelperHoneycomb(activity);
		} else {
			return new TabHelperEclair(activity);
		}
	}

	public CompatTab newTab(String tag) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			return new CompatTabHoneycomb(activity, tag);
		} else {
			return new CompatTabEclair(activity, tag);
		}

	}

	public abstract void addTab(CompatTab tab);

	protected abstract void onSaveInstanceState(Bundle outState);

	protected abstract void onRestoreInstanceState(Bundle savedInstanceState);

	protected abstract void setUp();
}
