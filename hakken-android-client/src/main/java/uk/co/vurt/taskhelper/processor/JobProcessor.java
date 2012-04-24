package uk.co.vurt.taskhelper.processor;

import java.util.Date;
import java.util.List;

import uk.co.vurt.hakken.domain.job.JobDefinition;
import uk.co.vurt.hakken.domain.task.Page;
import uk.co.vurt.hakken.domain.task.pageitem.PageItem;
import uk.co.vurt.taskhelper.providers.Job;
import uk.co.vurt.taskhelper.providers.Task;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

public class JobProcessor {

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
	
//	private Page currentPage;
	private List<Page> pages;
	int currentPagePosition = 0;
	
	public JobProcessor(Cursor cursor, ContentResolver contentResolver){
		this.contentResolver = contentResolver;
		
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
}
