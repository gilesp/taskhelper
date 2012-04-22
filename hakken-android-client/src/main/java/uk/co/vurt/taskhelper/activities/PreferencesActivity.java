package uk.co.vurt.taskhelper.activities;

import uk.co.vurt.taskhelper.R;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.View;
import android.widget.TextView;

public class PreferencesActivity extends PreferenceActivity {

	private TextView message;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

//		boolean returnToStart = getIntent().getBooleanExtra(DispatcherActivity.RETURN_TO_START_KEY, false);

		message = (TextView) findViewById(R.id.message);
		
		addPreferencesFromResource(R.xml.preferences);
		setContentView(R.layout.preferences_activity);
		
		
//		if(returnToStart){
//			startActivity(new Intent(this, DispatcherActivity.class));
//			finish();
//		}
	}
	
	public void handleSavePreferences(View view) {
		startActivity(new Intent(this, DispatcherActivity.class));
		finish();
	}
}




