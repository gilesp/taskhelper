package uk.co.vurt.hakken.ui.tabs;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public abstract class TabCompatActivity extends FragmentActivity {

	TabHelper tabHelper;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		tabHelper = TabHelper.createInstance(this);
	}
	
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		tabHelper.onSaveInstanceState(outState);
	}
	
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		tabHelper.onRestoreInstanceState(savedInstanceState);
	}
	
	protected TabHelper getTabHelper(){
		tabHelper.setUp();
		return tabHelper;
	}
}
