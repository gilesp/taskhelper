package uk.co.vurt.taskhelper.activities;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.co.vurt.hakken.domain.job.DataItem;
import uk.co.vurt.hakken.domain.job.JobDefinition;
import uk.co.vurt.hakken.domain.task.Page;
import uk.co.vurt.hakken.domain.task.pageitem.PageItem;
import uk.co.vurt.taskhelper.R;
import uk.co.vurt.taskhelper.processor.JobProcessor;
import uk.co.vurt.taskhelper.processor.TaskProcessor;
import uk.co.vurt.taskhelper.providers.Dataitem;
import uk.co.vurt.taskhelper.providers.Job;
import uk.co.vurt.taskhelper.providers.Task;
import uk.co.vurt.taskhelper.ui.WidgetFactory;
import uk.co.vurt.taskhelper.ui.widget.LabelledCheckBox;
import uk.co.vurt.taskhelper.ui.widget.LabelledDatePicker;
import uk.co.vurt.taskhelper.ui.widget.LabelledEditBox;
import uk.co.vurt.taskhelper.ui.widget.LabelledSpinner;
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
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RunJob extends Activity {

	private static final String TAG = "RunJob";
	
//	private static final String[] JOB_PROJECTION = new String[] {
//		Job.Definitions._ID,
//		Job.Definitions.NAME,
//		Job.Definitions.TASK_DEFINITION_ID,
//		Job.Definitions.CREATED,
//		Job.Definitions.DUE,
//		Job.Definitions.STATUS,
//		Job.Definitions.NOTES
//	};
//	
//	private static final int COLUMN_INDEX_JOB_ID = 0;
//	private static final int COLUMN_INDEX_JOB_NAME = 1;
//	private static final int COLUMN_INDEX_JOB_TASKDEFINITION_ID = 2;
//	private static final int COLUMN_INDEX_JOB_CREATED = 3;
//	private static final int COLUMN_INDEX_JOB_DUE = 4;
//	private static final int COLUMN_INDEX_JOB_STATUS = 5;
//	private static final int COLUMN_INDEX_JOB_NOTES = 6;
//	
//	private static final String[] DEFINITION_PROJECTION = new String[] {
//		Task.Definitions._ID,
//		Task.Definitions.JSON
//	};
//	
//	private static final int COLUMN_INDEX_TASKDEFINITION_ID = 0;
//	private static final int COLUMN_INDEX_TASKDEFINITION_JSON = 1;
	
	//Shouldn't this be an enum? does android support enums?
	private static final int STATE_RUN = 0;
	
	private int state;
	private Uri uri;
	private Cursor jobCursor;
//	private Cursor definitionCursor;
	
	private ContentResolver contentResolver;
//	private int jobId;
	
	
	private LinearLayout pageContent;
	private LinearLayout buttonBar;
	
//	private TaskDefinition taskDefinition;
	private JobProcessor jobProcessor;
	
//	private JobDefinition job;
	
	private Map<String, View> widgetMap;
	
	private Button finishButton;
	private Button nextButton;
	private Button previousButton;
	
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
		//Get the job definition
		jobCursor = managedQuery(uri, JobProcessor.JOB_PROJECTION, null, null, null);
		jobProcessor = new JobProcessor(jobCursor, contentResolver);

		widgetMap = new HashMap<String, View>();
		
		//Setup buttons
		previousButton = new Button(this);
		previousButton.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		previousButton.setText("Previous");
		previousButton.setOnClickListener(new Button.OnClickListener(){

			public void onClick(View view) {
				savePage(jobProcessor.getCurrentPage());
				jobProcessor.previousPage();
				drawPage();
				return;
			}
			
		});
		
		nextButton = new Button(this);
		nextButton.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		nextButton.setText("Next");
		nextButton.setOnClickListener(new Button.OnClickListener(){

			public void onClick(View view) {
				savePage(jobProcessor.getCurrentPage());
				jobProcessor.nextPage();
				drawPage();
				return;
			}
			
		});
		
		finishButton = new Button(this);
		finishButton.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		finishButton.setText("Finish");
		finishButton.setOnClickListener(new Button.OnClickListener(){

			public void onClick(View view) {
				savePage(jobProcessor.getCurrentPage());
				finishJob();
				return;
			}
			
		});
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		drawPage();
	}
	
	protected void finishJob(){
		jobProcessor.finish();
		RunJob.this.finish();
	}
	
	protected void savePage(Page currentPage){
		Log.d(TAG, "Saving page: " + currentPage);
		List<PageItem> items = currentPage.getItems();
		for(PageItem item: items){
			String widgetKey = createWidgetKey(jobProcessor.getPageName(), item);
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
				} else if("YESNO".equals(item.getType())){
					LabelledCheckBox checkBox = (LabelledCheckBox)widget;
					value = Boolean.toString(checkBox.getValue());
				} else if("SELECT".equals(item.getType())){
					LabelledSpinner spinner = (LabelledSpinner)widget;
					if(spinner.isMultiSelect()){
						//serialise values to a comma separated list.
						String[] values = spinner.getSelectedValues();
						StringBuffer valueBuffer = new StringBuffer();
						for(int i = 0; i < values.length; i++){
							valueBuffer.append(values[i]);
							if(i < values.length - 1){
								valueBuffer.append(",");
							}
						}
						value = valueBuffer.toString();
						valueBuffer = null;
					}else {
						value = spinner.getSelectedValue();
					}
					
				}
				if(value != null){
					//TODO: Do I need to bother with the dataitem class at all?
					DataItem dataItem = new DataItem(currentPage.getName(), item.getName(), item.getType(), value);
					ContentValues values = new ContentValues();
					values.put(Dataitem.Definitions.JOB_ID, jobProcessor.getJobId());
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
														  new String[]{""+jobProcessor.getJobId(), dataItem.getPageName(), dataItem.getName(), dataItem.getType()}/*args*/, 
														  null /*sort order*/);
					int count = 0;
					if(cursor != null){
						cursor.moveToFirst();
						if(cursor.getCount() > 0){
							int dataItemId = cursor.getInt(0);
							Log.d(TAG, "Updating dataitem: " + Uri.withAppendedPath(Dataitem.Definitions.CONTENT_URI, ""+dataItemId) + "     " + dataItem.getName() + ":" + dataItem.getValue());
							count = contentResolver.update(Uri.withAppendedPath(Dataitem.Definitions.CONTENT_URI, ""+dataItemId), values, null, null);
						}
						cursor.close();
					}
					if(count <= 0){
						Log.d(TAG, "Saving new dataitem: " + dataItem.getName() + ":" + dataItem.getValue());
						Uri dataItemUri = contentResolver.insert(Dataitem.Definitions.CONTENT_URI, values);
						Log.d(TAG, "Saved URI: " + dataItemUri);
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
	
	private String createWidgetKey(String pageName, PageItem item){
		return pageName + "_" + item.getName() + "_" + item.getType();
	}
	
	protected void drawPage(){
		
	
//		Log.d(TAG, "Cursor: " + jobCursor);
//		if(jobCursor != null){
//			jobCursor.moveToFirst();
			Log.d(TAG, "state: " + state);
			if(state == STATE_RUN){
//				jobId = jobCursor.getInt(COLUMN_INDEX_JOB_ID);
//				String jobName = jobCursor.getString(COLUMN_INDEX_JOB_NAME);
//				int definitionId = jobCursor.getInt(COLUMN_INDEX_JOB_TASKDEFINITION_ID);
//				Date jobCreated = new Date(jobCursor.getLong(COLUMN_INDEX_JOB_CREATED));
//				Date jobDue = new Date(jobCursor.getLong(COLUMN_INDEX_JOB_DUE));
//				String jobStatus = jobCursor.getString(COLUMN_INDEX_JOB_STATUS);
//				String notes = jobCursor.getString(COLUMN_INDEX_JOB_NOTES);
				
//				Uri definitionUri = ContentUris.withAppendedId(Task.Definitions.CONTENT_URI, definitionId);
				
//				definitionCursor = contentResolver.query(definitionUri, DEFINITION_PROJECTION, null, null, null);
//				if(definitionCursor != null){
//					definitionCursor.moveToFirst();
//					taskDefinition = new Gson().fromJson(definitionCursor.getString(COLUMN_INDEX_TASKDEFINITION_JSON), TaskDefinition.class);
//					definitionCursor.close();
//				}
//				job = new JobDefinition(jobId, jobName, taskDefinition, jobCreated, jobDue, jobStatus, notes);
				
//				setTitle("Running " + jobProcessor.getTaskName());
				
				pageContent.removeAllViewsInLayout();
				
				setTitle(jobProcessor.getPageName());
				
				List<PageItem> items = jobProcessor.getPageItems();
				
				if(items != null){
					Log.d(TAG, "Items: " + items.size());
					for(PageItem item: items){
						Log.d(TAG, "Current item: " + item);
						String widgetKey = createWidgetKey(jobProcessor.getPageName(), item);

						View widget = null;
						if(widgetMap.containsKey(widgetKey)){
							//retrieve widget from map
							widget = widgetMap.get(widgetKey);
						} else {
							//new widget, so create it
							widget = WidgetFactory.createWidget(this, item, retrieveDataItem(jobProcessor.getJobId(), jobProcessor.getPageName(), item.getName(), item.getType()));
							
							//TODO: Figure out better way of handling this, hopefully within the widget factory itself.
							if("DATETIME".equals(item.getType())){
								final LabelledDatePicker datePicker = ((LabelledDatePicker)widget);
								datePicker.setOnClickListener(new View.OnClickListener() {
									@Override
									public void onClick(View v) {
										showDatePickerDialog(datePicker);
									}
								});
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
				
				if(jobProcessor.previousPages()){
					buttonBar.addView(previousButton);
				}
				if(jobProcessor.morePages()){
					if(jobProcessor.lastPage()){
						buttonBar.addView(finishButton);
					} else {
						buttonBar.addView(nextButton);
					}
				} 
				
			}
//		}
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
