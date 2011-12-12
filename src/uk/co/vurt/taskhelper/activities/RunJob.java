package uk.co.vurt.taskhelper.activities;

import java.util.Date;
import java.util.List;

import uk.co.vurt.taskhelper.R;
import uk.co.vurt.taskhelper.domain.definition.Page;
import uk.co.vurt.taskhelper.domain.definition.PageItem;
import uk.co.vurt.taskhelper.domain.definition.TaskDefinition;
import uk.co.vurt.taskhelper.domain.job.JobDefinition;
import uk.co.vurt.taskhelper.providers.Job;
import uk.co.vurt.taskhelper.providers.Task;
import uk.co.vurt.taskhelper.ui.widget.LabelledDatePicker;
import uk.co.vurt.taskhelper.ui.widget.LabelledEditBox;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

public class RunJob extends Activity {

	private static final String TAG = "RunJob";
	
	private static final String[] JOB_PROJECTION = new String[] {
		Job.Definitions._ID,
		Job.Definitions.NAME,
		Job.Definitions.TASK_DEFINITION_ID,
		Job.Definitions.CREATED,
		Job.Definitions.DUE,
		Job.Definitions.STATUS
	};
	
	private static final int COLUMN_INDEX_JOB_ID = 0;
	private static final int COLUMN_INDEX_JOB_NAME = 1;
	private static final int COLUMN_INDEX_JOB_TASKDEFINITION_ID = 2;
	private static final int COLUMN_INDEX_JOB_CREATED = 3;
	private static final int COLUMN_INDEX_JOB_DUE = 4;
	private static final int COLUMN_INDEX_JOB_STATUS = 5;
	
	private static final String[] DEFINITION_PROJECTION = new String[] {
		Task.Definitions._ID,
		Task.Definitions.JSON
	};
	
	private static final int COLUMN_INDEX_TASKDEFINITION_ID = 0;
	private static final int COLUMN_INDEX_TASKDEFINITION_JSON = 1;
	
	//Shouldn't this be an enum? does android support enums?
	private static final int STATE_RUN = 0;
	
	private int state;
	private Uri uri;
	private Cursor jobCursor;
	private Cursor definitionCursor;
	private int currentPageId = 0;
	private ContentResolver contentResolver;

	
	private LinearLayout pageContent;
	private LinearLayout buttonBar;
	
	private TaskDefinition taskDefinition;
	private JobDefinition job;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		contentResolver = getContentResolver();
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
		
		buttonBar = (LinearLayout)findViewById(R.id.buttonBar);
		pageContent = (LinearLayout)findViewById(R.id.pageContent);
		
		Log.d(TAG, "Job URI: " + uri);
		//Get the task definition
		jobCursor = managedQuery(uri, JOB_PROJECTION, null, null, null);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		drawPage();
	}
	
	protected void drawPage(){
		Log.d(TAG, "Cursor: " + jobCursor);
		if(jobCursor != null){
			jobCursor.moveToFirst();
			Log.d(TAG, "state: " + state);
			if(state == STATE_RUN){
				int jobId = jobCursor.getInt(COLUMN_INDEX_JOB_ID);
				String jobName = jobCursor.getString(COLUMN_INDEX_JOB_NAME);
				int definitionId = jobCursor.getInt(COLUMN_INDEX_JOB_TASKDEFINITION_ID);
				Date jobCreated = new Date(jobCursor.getLong(COLUMN_INDEX_JOB_CREATED));
				Date jobDue = new Date(jobCursor.getLong(COLUMN_INDEX_JOB_DUE));
				String jobStatus = jobCursor.getString(COLUMN_INDEX_JOB_STATUS);
				
				Uri definitionUri = ContentUris.withAppendedId(Task.Definitions.CONTENT_URI, definitionId);
				Log.d(TAG, "Definition URI: " + definitionUri.toString());
				definitionCursor = contentResolver.query(definitionUri, DEFINITION_PROJECTION, null, null, null);
				if(definitionCursor != null){
					definitionCursor.moveToFirst();
					taskDefinition = new Gson().fromJson(definitionCursor.getString(COLUMN_INDEX_TASKDEFINITION_JSON), TaskDefinition.class);
					definitionCursor.close();
				}
				job = new JobDefinition(jobId, jobName, taskDefinition, jobCreated, jobDue, jobStatus);
				
				setTitle("Running " + taskDefinition.getName());
				
				pageContent.removeAllViewsInLayout();
				
				List<Page> pages = taskDefinition.getPages();
				Page currentPage = pages.get(currentPageId);
				Log.d(TAG, "Current page: " + currentPage);
				setTitle(taskDefinition.getName() + ": " + currentPage.getName());
				
				List<PageItem> items = currentPage.getItems();
				if(items != null){
					Log.d(TAG, "Items: " + items.size());
					for(PageItem item: items){
						Log.d(TAG, "Current item: " + item);
						if("LABEL".equals(item.getType())){
							TextView label = new TextView(this);
							label.setText(item.getValue());
							pageContent.addView(label);
							Log.d(TAG, "Added TextView label");
						}else if("TEXT".equals(item.getType())){
							LabelledEditBox editBox = new LabelledEditBox(this, item.getLabel(), item.getValue());
							pageContent.addView(editBox);
							Log.d(TAG, "Added LabelledEditbox");
						}else if("DIGITS".equals(item.getType())){
							LabelledEditBox editBox = new LabelledEditBox(this, item.getLabel(), item.getValue());
							pageContent.addView(editBox);
							Log.d(TAG, "Added LabelledEditbox");
						}else if("DATETIME".equals(item.getType())){
							LabelledDatePicker datePicker = new LabelledDatePicker(this, item.getLabel(), item.getValue());
							pageContent.addView(datePicker);
							Log.d(TAG, "Added LabelledEditbox");
						} else {
							TextView errorLabel = new TextView(this);
							errorLabel.setText("Unknown item: '" + item.getType() + "'");
							pageContent.addView(errorLabel);
							Log.d(TAG, "Unknown item: '" + item.getType() + "'");
						}
					}
				} else {
					TextView errorLabel = new TextView(this);
					errorLabel.setText("No items were defined for this page.");
					pageContent.addView(errorLabel);
					Log.d(TAG, "No items defined for this page.");
				}
				
				//define next/previous/finish buttons
				buttonBar.removeAllViewsInLayout(); 
				if(currentPageId > 0){
					Button previousButton = new Button(this);
					previousButton.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
					previousButton.setText("Previous");
					previousButton.setOnClickListener(new Button.OnClickListener(){

						public void onClick(View view) {
							//TODO: save form data
							currentPageId--;
							drawPage();
							return;
						}
						
					});
					buttonBar.addView(previousButton);
				}
				if((currentPageId+1) < pages.size()){
					Button nextButton = new Button(this);
					nextButton.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
					nextButton.setText("Next");
					nextButton.setOnClickListener(new Button.OnClickListener(){

						public void onClick(View view) {
							//TODO: save form data
							currentPageId++;
							drawPage();
							return;
						}
						
					});

					buttonBar.addView(nextButton);
				} else if((currentPageId+1) == pages.size()){
					Button finishButton = new Button(this);
					finishButton.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
					finishButton.setText("Finish");
					finishButton.setOnClickListener(new Button.OnClickListener(){

						public void onClick(View view) {
							//TODO: save form data
							currentPageId = 0;
							RunJob.this.finish();
							return;
						}
						
					});

					buttonBar.addView(finishButton);
				}
			}
		}
	}
}
