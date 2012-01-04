package uk.co.vurt.taskhelper.activities;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.co.vurt.taskhelper.R;
import uk.co.vurt.taskhelper.domain.definition.Page;
import uk.co.vurt.taskhelper.domain.definition.PageItem;
import uk.co.vurt.taskhelper.domain.definition.TaskDefinition;
import uk.co.vurt.taskhelper.domain.job.DataItem;
import uk.co.vurt.taskhelper.domain.job.JobDefinition;
import uk.co.vurt.taskhelper.providers.Dataitem;
import uk.co.vurt.taskhelper.providers.Job;
import uk.co.vurt.taskhelper.providers.Task;
import uk.co.vurt.taskhelper.ui.widget.LabelledDatePicker;
import uk.co.vurt.taskhelper.ui.widget.LabelledEditBox;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
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
	private int jobId;
	
	
	private LinearLayout pageContent;
	private LinearLayout buttonBar;
	
	private TaskDefinition taskDefinition;
	private JobDefinition job;
	
	private Map<String, View> widgetMap;
	
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
		
		requestWindowFeature(Window.FEATURE_LEFT_ICON);
		setContentView(R.layout.run_task);
		//TODO: Make this dynamic, based on the task definition type
		setFeatureDrawableResource(Window.FEATURE_LEFT_ICON, R.drawable.hsc_logo);
		
		buttonBar = (LinearLayout)findViewById(R.id.buttonBar);
		pageContent = (LinearLayout)findViewById(R.id.pageContent);
		
		Log.d(TAG, "Job URI: " + uri);
		//Get the task definition
		jobCursor = managedQuery(uri, JOB_PROJECTION, null, null, null);
		
		widgetMap = new HashMap<String, View>();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		drawPage();
	}
	
	protected void finishJob(){
		job.setStatus("COMPLETED");
		ContentValues values = new ContentValues();
		values.put(Job.Definitions.STATUS, job.getStatus());
		contentResolver.update(Uri.withAppendedPath(Job.Definitions.CONTENT_URI, ""+job.getId()) , values, null, null);
		RunJob.this.finish();
	}
	
	protected void savePage(Page currentPage){
		Log.d(TAG, "Saving page: " + currentPage);
		List<PageItem> items = currentPage.getItems();
		for(PageItem item: items){
			String widgetKey = createWidgetKey(currentPage, item);
			View widget = widgetMap.get(widgetKey);
			
			if(widget != null){
				String value = null;
				if("TEXT".equals(item.getType())){
					LabelledEditBox editBox = (LabelledEditBox)widget;
					value = editBox.getValue();
				}else if("DIGITS".equals(item.getType())){
					LabelledEditBox editBox = (LabelledEditBox)widget;
					value = editBox.getValue();
				}else if("DATETIME".equals(item.getType())){
					LabelledDatePicker datePicker = (LabelledDatePicker)widget;
					value = datePicker.getValue();
				}
				if(value != null){
					//TODO: Do I need to bother with the dataitem class at all?
					DataItem dataItem = new DataItem(currentPage.getName(), item.getName(), item.getType(), value);
					ContentValues values = new ContentValues();
					values.put(Dataitem.Definitions.JOB_ID, jobId);
					values.put(Dataitem.Definitions.PAGENAME, dataItem.getPageName());
					values.put(Dataitem.Definitions.NAME, dataItem.getName());
					values.put(Dataitem.Definitions.TYPE, dataItem.getType());
					values.put(Dataitem.Definitions.VALUE, dataItem.getValue());
					
					Cursor cursor = contentResolver.query(Dataitem.Definitions.CONTENT_URI, 
														  new String[]{Dataitem.Definitions._ID} /*projection*/,
														  Dataitem.Definitions.JOB_ID+ "=? "
														  + "AND " + Dataitem.Definitions.PAGENAME + "=? "
														  + "AND " + Dataitem.Definitions.NAME + "=? " 
														  + "AND " + Dataitem.Definitions.TYPE + "=?" /*where*/, 
														  new String[]{""+jobId, dataItem.getPageName(), dataItem.getName(), dataItem.getType()}/*args*/, 
														  null /*sort order*/);
					int count = 0;
					if(cursor != null){
						cursor.moveToFirst();
						if(cursor.getCount() > 0){
							int dataItemId = cursor.getInt(0);
							count = contentResolver.update(Uri.withAppendedPath(Dataitem.Definitions.CONTENT_URI, ""+dataItemId), values, null, null);
						}
						cursor.close();
					}
					if(count <= 0){
						Uri dataItemUri = contentResolver.insert(Dataitem.Definitions.CONTENT_URI, values);
					}
					//TODO: Do something with this uri?
				}
			}else {
				Log.e(TAG, "Unable to retrieve widget with key: " + widgetKey);
			}
		}
		
	}
	
	private DataItem retrieveDataItem(int jobId, String pageName, String name, String type){
		DataItem dataitem = null;
		Cursor cursor = contentResolver.query(Dataitem.Definitions.CONTENT_URI, 
				  new String[]{Dataitem.Definitions._ID, Dataitem.Definitions.VALUE} /*projection*/,
				  Dataitem.Definitions.JOB_ID+ "=? "
				  + "AND " + Dataitem.Definitions.PAGENAME + "=? "
				  + "AND " + Dataitem.Definitions.NAME + "=? " 
				  + "AND " + Dataitem.Definitions.TYPE + "=?" /*where*/, 
				  new String[]{""+jobId, pageName, name, type}/*args*/, 
				  null /*sort order*/);
		
		if(cursor != null){
			cursor.moveToFirst();
			if(cursor.getCount() > 0){
				dataitem = new DataItem(pageName, name, type, cursor.getString(1));
			}
			cursor.close();
		}
		
		return dataitem;
	}
	
	private String createWidgetKey(Page page, PageItem item){
		return page.getName() + "_" + item.getName() + "_" + item.getType();
	}
	
	protected void drawPage(){
		
		
		
		Log.d(TAG, "Cursor: " + jobCursor);
		if(jobCursor != null){
			jobCursor.moveToFirst();
			Log.d(TAG, "state: " + state);
			if(state == STATE_RUN){
				jobId = jobCursor.getInt(COLUMN_INDEX_JOB_ID);
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
				//TODO: handle tasks with no pages
				final Page currentPage = pages.get(currentPageId);
				Log.d(TAG, "Current page: " + currentPage);
				setTitle(taskDefinition.getName() + ": " + currentPage.getName());
				
				List<PageItem> items = currentPage.getItems();
				if(items != null){
					Log.d(TAG, "Items: " + items.size());
					for(PageItem item: items){
						Log.d(TAG, "Current item: " + item);
						String widgetKey = createWidgetKey(currentPage, item);
						View widget = null;
						if(widgetMap.containsKey(widgetKey)){
							//retrieve widget from map
							widget = widgetMap.get(widgetKey);
						} else {
							//Retrieve value from dataitem if present
							//If we have a dataitem value, use that in preference to any job specified value.
							DataItem dataItem = retrieveDataItem(jobId, currentPage.getName(), item.getName(), item.getType());
							//create new widget and add it to the map
							if("LABEL".equals(item.getType())){
								TextView label = new TextView(this);
								label.setText(item.getLabel());
								widget = label;
								Log.d(TAG, "Added TextView label");
							}else if("TEXT".equals(item.getType())){
								LabelledEditBox editBox = new LabelledEditBox(this, item.getLabel(), dataItem != null ? dataItem.getValue() : item.getValue());
								widget = editBox;
								Log.d(TAG, "Added LabelledEditbox");
							}else if("DIGITS".equals(item.getType())){
								LabelledEditBox editBox = new LabelledEditBox(this, item.getLabel(), dataItem != null ? dataItem.getValue() :  item.getValue());
								editBox.setKeyListener(new DigitsKeyListener());
								widget = editBox;
								Log.d(TAG, "Added LabelledEditbox");
							}else if("DATETIME".equals(item.getType())){
								final LabelledDatePicker datePicker = new LabelledDatePicker(this, item.getLabel(), dataItem != null ? dataItem.getValue() :  item.getValue());
								datePicker.setOnClickListener(new View.OnClickListener() {
									@Override
									public void onClick(View v) {
										showDatePickerDialog(datePicker);
									}
								});
								widget = datePicker;
								Log.d(TAG, "Added LabelledEditbox");
							} else {
								TextView errorLabel = new TextView(this);
								errorLabel.setText("Unknown item: '" + item.getType() + "'");
								widget = errorLabel;
								Log.d(TAG, "Unknown item: '" + item.getType() + "'");
							}

							widgetMap.put(widgetKey, widget);
						}
						
						pageContent.addView(widget);
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
							savePage(currentPage);
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
							savePage(currentPage);
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
							savePage(currentPage);
							currentPageId = 0;
							finishJob();
							return;
						}
						
					});

					buttonBar.addView(finishButton);
				}
			}
		}
	}
	
	static final int DATE_DIALOG_ID = 0;
	
	private int mYear;
	private int mMonth;
	private int mDay;
	
	private LabelledDatePicker currentDatePicker;
	
	private void showDatePickerDialog(LabelledDatePicker datePicker){
		currentDatePicker = datePicker;
		//TODO: Extract default date value from labelled date picker
		Calendar cal = Calendar.getInstance();
		mYear = cal.get(Calendar.YEAR);
		mMonth = cal.get(Calendar.MONTH);
		mDay = cal.get(Calendar.DAY_OF_MONTH);
		showDialog(DATE_DIALOG_ID);
	}
	
	// the callback received when the user "sets" the date in the dialog
    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year, 
                                      int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                    updateDisplay();
                }
            };
            
    private void updateDisplay() {
    	currentDatePicker.setValue(new StringBuilder()
        .append(mDay).append("-")
        // Month is 0 based so add 1
        .append(mMonth + 1).append("-")
        .append(mYear).append(" ").toString());
    }
    
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        case DATE_DIALOG_ID:
            return new DatePickerDialog(this,
                        mDateSetListener,
                        mYear, mMonth, mDay);
        }
        return null;
    }
}
