package uk.co.vurt.taskhelper.activities;

import uk.co.vurt.taskhelper.R;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;

public class PreferencesActivity extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		boolean returnToStart = getIntent().getBooleanExtra(DispatcherActivity.RETURN_TO_START_KEY, false);
		
		addPreferencesFromResource(R.xml.preferences);
		
		if(returnToStart){
			startActivity(new Intent(this, DispatcherActivity.class));
			finish();
		}
	}
	
	
}




