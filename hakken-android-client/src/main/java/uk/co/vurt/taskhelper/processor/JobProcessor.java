package uk.co.vurt.taskhelper.processor;

import java.util.Date;
import java.util.List;

import uk.co.vurt.hakken.domain.job.DataItem;
import uk.co.vurt.hakken.domain.job.JobDefinition;
import uk.co.vurt.hakken.domain.task.Page;
import uk.co.vurt.hakken.domain.task.pageitem.PageItem;
import uk.co.vurt.taskhelper.providers.Dataitem;
import uk.co.vurt.taskhelper.providers.Job;
import uk.co.vurt.taskhelper.providers.Task;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

public class JobProcessor {

	private static final String TAG = "JobProcessor";
		
	public static final String[] JOB_PROJECTION = new String[] {
		Job.Definitions._ID,
		Job.Definitions.NAME,
		Job.Definitions.TASK_DEFINITION_ID,
		Job.Definitions.CREATED,
		Job.Definitions.DUE,
		Job.Definitions.STATUS,
		Job.Definitions.NOTES
	};
	
	private static final int COLUMN_INDEX_JOB_ID = 0;
	private static final int COLUMN_INDEX_JOB_NAME = 1;
	private static final int COLUMN_INDEX_JOB_TASKDEFINITION_ID = 2;
	private static final int COLUMN_INDEX_JOB_CREATED = 3;
	private static final int COLUMN_INDEX_JOB_DUE = 4;
	private static final int COLUMN_INDEX_JOB_STATUS = 5;
	private static final int COLUMN_INDEX_JOB_NOTES = 6;

	private ContentResolver contentResolver;
	private Cursor cursor;
	private TaskProcessor taskProcessor;
	private JobDefinition jobDefinition;
	
	private List<Page> pages;
	int currentPagePosition = 0;
	
	public JobProcessor(ContentResolver contentResolver, Uri jobUri){
		Log.d(TAG, "Instantiating with URI: " + jobUri);
		
		this.contentResolver = contentResolver;
		
		cursor = contentResolver.query(jobUri, JOB_PROJECTION, null, null, null);
		
		if(cursor != null){
			cursor.moveToFirst();
			int jobId = cursor.getInt(COLUMN_INDEX_JOB_ID);
			String jobName = cursor.getString(COLUMN_INDEX_JOB_NAME);
			Date jobCreated = new Date(cursor.getLong(COLUMN_INDEX_JOB_CREATED));
			Date jobDue = new Date(cursor.getLong(COLUMN_INDEX_JOB_DUE));
			String jobStatus = cursor.getString(COLUMN_INDEX_JOB_STATUS);
			String notes = cursor.getString(COLUMN_INDEX_JOB_NOTES);

			//initialise the TaskProcessor
			Uri definitionUri = ContentUris.withAppendedId(Task.Definitions.CONTENT_URI, cursor.getInt(COLUMN_INDEX_JOB_TASKDEFINITION_ID));
			taskProcessor = new TaskProcessor(contentResolver, definitionUri);
			
			jobDefinition = new JobDefinition(jobId, jobName, taskProcessor.getTaskDefinition(), jobCreated, jobDue, jobStatus, notes);
			
			pages = taskProcessor.getPages();
		}
	}
	
	public String getTaskName(){
		return taskProcessor.getTaskName();
	}
	
	public List<PageItem> getPageItems(){
		if(pages != null){
			return getCurrentPage().getItems(); 
		}else {
			return null;
		}
	}
	
	public String getPageName(){
		return getCurrentPage().getName();
	}
	
	public boolean previousPages(){
		//(currentPageId+1) < pages.size()
		return currentPagePosition > 0;
	}
	
	public boolean morePages(){
		return currentPagePosition < pages.size();
	}
	
	public boolean lastPage(){
		//(currentPageId+1) == pages.size()
		return currentPagePosition + 1 == pages.size();
	}
	
	public void nextPage(){
		if(morePages()){
			currentPagePosition++;
		}
	}
	
	public void previousPage(){
		if(previousPages()){
			currentPagePosition--;
		}
	}
	
	public Page getCurrentPage(){
		if(pages != null){
			return pages.get(currentPagePosition); 
		}else {
			return null;
		}
	}
	
	public void finish(){
		ContentValues values = new ContentValues();
		values.put(Job.Definitions.STATUS, "COMPLETED");
		contentResolver.update(Uri.withAppendedPath(Job.Definitions.CONTENT_URI, ""+jobDefinition.getId()) , values, null, null);
	}
	
	public int getJobId(){
		return jobDefinition.getId();
	}
	
	/*
	 * Currently, dataItems are persisted straight away to the contentResolver.
	 * Is this the best thing to do? Yes it's good from a convenience point of 
	 * view (i.e. we don't need to worry about the application being put to 
	 * sleep or destroyed) but is it bad in terms of performance?
	 * 
	 * Need to do some performance testing to determine if a local dataitem
	 * cache would be of benefit. 
	 */
	public Uri storeDataItem(DataItem dataItem){
		Uri dataItemUri = null;
		
		ContentValues values = new ContentValues();
		values.put(Dataitem.Definitions.JOB_ID, getJobId());
		values.put(Dataitem.Definitions.PAGENAME, dataItem.getPageName());
		values.put(Dataitem.Definitions.NAME, dataItem.getName());
		values.put(Dataitem.Definitions.TYPE, dataItem.getType());
		values.put(Dataitem.Definitions.VALUE, dataItem.getValue());

		Cursor cursor = contentResolver
				.query(Dataitem.Definitions.CONTENT_URI,
						new String[] { Dataitem.Definitions._ID },
						Dataitem.Definitions.JOB_ID + "=? "
								+ "AND " + Dataitem.Definitions.PAGENAME + "=? " 
								+ "AND " + Dataitem.Definitions.NAME + "=? "
								+ "AND " + Dataitem.Definitions.TYPE + "=?",
						new String[] {
								"" + getJobId(),
								dataItem.getPageName(),
								dataItem.getName(),
								dataItem.getType() },
						null);
		int count = 0;
		if (cursor != null) {
			cursor.moveToFirst();
			if (cursor.getCount() > 0) {
				int dataItemId = cursor.getInt(0);
				dataItemUri = Uri.withAppendedPath(
							Dataitem.Definitions.CONTENT_URI,
							"" + dataItemId);
				Log.d(TAG,
						"Updating dataitem: "
								+ dataItemUri + "     "
								+ dataItem.getName() + ":"
								+ dataItem.getValue());
				count = contentResolver.update(dataItemUri, values, null, null);
			}
			cursor.close();
		}
		if (count <= 0) {
			Log.d(TAG, "Saving new dataitem: " + dataItem.getName()
					+ ":" + dataItem.getValue());
			dataItemUri = contentResolver.insert(
					Dataitem.Definitions.CONTENT_URI, values);
			Log.d(TAG, "Saved URI: " + dataItemUri);
		}
		
		return dataItemUri;
	}
	
	
	public DataItem retrieveDataItem(String pageName, String name, String type){
		DataItem dataitem = null;
		Cursor cursor = contentResolver.query(Dataitem.Definitions.CONTENT_URI,
				new String[] { Dataitem.Definitions._ID,
						Dataitem.Definitions.VALUE },
				Dataitem.Definitions.JOB_ID + "=? " 
						+ "AND " + Dataitem.Definitions.PAGENAME + "=? " 
						+ "AND " + Dataitem.Definitions.NAME + "=? " 
						+ "AND " + Dataitem.Definitions.TYPE + "=?",
				new String[] { "" + getJobId(), pageName, name, type },
				null);

		if (cursor != null) {
			cursor.moveToFirst();
			if (cursor.getCount() > 0) {
				dataitem = new DataItem(pageName, name, type, cursor.getString(1));
			}
			cursor.close();
		}

		return dataitem;
	}
}
