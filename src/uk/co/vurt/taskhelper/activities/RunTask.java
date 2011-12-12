package uk.co.vurt.taskhelper.activities;

import uk.co.vurt.taskhelper.R;
import uk.co.vurt.taskhelper.providers.Task;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class RunTask extends Activity {

	private static final String TAG = "RunTask";

	private static final String[] PROJECTION = new String[] {
		Task.Definitions._ID, //0
		Task.Definitions.NAME, //1
		Task.Definitions.DESCRIPTION //2
	};
	
	private static final int COLUMN_INDEX_ID = 0;
	private static final int COLUMN_INDEX_NAME = 1;
	private static final int COLUMN_INDEX_DESCRIPTION = 2;
	
	//Shouldn't this be an enum? does android support enums?
	private static final int STATE_RUN = 0;
	
	private int state;
	private Uri uri;
	private Cursor cursor;
	
	private TextView idText;
	private TextView nameText;
	private TextView descriptionText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		final Intent intent = getIntent();
		final String action = intent.getAction();
		if(Intent.ACTION_RUN.equals(action)){
			state = STATE_RUN;
			uri = intent.getData();
		}else {
			Log.e(TAG, "Unknown action '" + intent.getAction() + "'. Exiting.");
			finish();
			return;
		}
		
		setContentView(R.layout.run_task);
		
//		idText = (TextView)findViewById(R.id.taskId);
//		nameText = (TextView)findViewById(R.id.taskName);
//		descriptionText = (TextView)findViewById(R.id.taskDescription);
		
		//Get the task definition
		cursor = managedQuery(uri, PROJECTION, null, null, null);
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		if(cursor != null){
			cursor.moveToFirst();
			
			if(state == STATE_RUN){
				int id = cursor.getInt(COLUMN_INDEX_ID);
				String name = cursor.getString(COLUMN_INDEX_NAME);
				String description = cursor.getString(COLUMN_INDEX_DESCRIPTION);
				setTitle("Running " + name);
				
				idText.setText(id + "");
				nameText.setText(name);
				descriptionText.setText(description);
			}
		}
	}
	
	
	
}
