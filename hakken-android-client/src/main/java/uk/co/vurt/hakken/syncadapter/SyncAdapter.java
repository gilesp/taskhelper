package uk.co.vurt.hakken.syncadapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.apache.http.auth.AuthenticationException;
import org.json.JSONException;

import uk.co.vurt.hakken.Constants;
import uk.co.vurt.hakken.authenticator.AuthenticatorActivity;
import uk.co.vurt.hakken.client.NetworkUtilities;
import uk.co.vurt.hakken.domain.JSONUtil;
import uk.co.vurt.hakken.domain.job.DataItem;
import uk.co.vurt.hakken.domain.job.JobDefinition;
import uk.co.vurt.hakken.domain.job.Submission;
import uk.co.vurt.hakken.domain.task.TaskDefinition;
import uk.co.vurt.hakken.providers.Dataitem;
import uk.co.vurt.hakken.providers.Job;
import uk.co.vurt.hakken.providers.Task;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncResult;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.ParseException;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;

public class SyncAdapter extends AbstractThreadedSyncAdapter {
	private static final String TAG = "SyncAdapter";
	private static final String LAST_UPDATED_KEY = "uk.co.vurt.hakken.syncadapter.lastUpdated";
	private static final boolean NOTIFY_AUTH_FAILURE = true;
	
	private final AccountManager accountManager;
	private final Context context;
	
//	private Date lastUpdated;
	
	public SyncAdapter(Context context, boolean autoInitialize) {
		super(context, autoInitialize);
		this.context = context;
		accountManager = AccountManager.get(context);
	}

	private void storeTaskDefinition(ContentProviderClient provider, TaskDefinition definition){
		if(definition != null){
			Log.d(TAG, "Adding a definition to database: " + definition);
			ContentValues values = new ContentValues();
			values.put(Task.Definitions._ID, definition.getId());
			values.put(Task.Definitions.NAME, definition.getName());
			values.put(Task.Definitions.DESCRIPTION, definition.getDescription());
			
			//Serialise the object as json, rather than individually storing pages etc.
			values.put(Task.Definitions.JSON, JSONUtil.getInstance().toJson(definition));
			
			//We need to see if the definition already exists, if it does do an update otherwise do an insert
			Uri definitionUri = ContentUris.withAppendedId(Task.Definitions.CONTENT_URI, definition.getId());
			
			Cursor cur = null;
			try {
				cur = provider.query(definitionUri, null, null, null, null);
				if(cur.moveToFirst()){
					Log.d(TAG, "Updating task definition " + definition.getId());
					provider.update(definitionUri, values, null, null);
				}else{
					Log.d(TAG, "Inserting new definition " + definition.getId());
					provider.insert(Task.Definitions.CONTENT_URI, values);
				}
			} catch (RemoteException re) {
				Log.e(TAG, "RemoteException", re);
			} finally {
				cur.close();
				cur = null;
			}
		}else{
			Log.d(TAG, "Null definition");
		}
	}
	
	private void storeDataItem(ContentProviderClient provider, DataItem dataItem, JobDefinition job){
		if(dataItem.getValue() != null && !"null".equals(dataItem.getValue())){
			Log.d(TAG, "Storing a dataitem for a job.");
			ContentValues values = new ContentValues();
			values.put(Dataitem.Definitions.JOB_ID, job.getId());
			values.put(Dataitem.Definitions.PAGENAME, dataItem.getPageName());
			values.put(Dataitem.Definitions.NAME, dataItem.getName());
			values.put(Dataitem.Definitions.TYPE, dataItem.getType());
			values.put(Dataitem.Definitions.VALUE, dataItem.getValue());
			
			try{
				provider.insert(Dataitem.Definitions.CONTENT_URI, values);
			}catch(SQLiteException e){
				try {
					provider.update(Dataitem.Definitions.CONTENT_URI, values, null, null);
				} catch (RemoteException re) {
					Log.e(TAG, "RemoteException", re);
				}
			}catch(RemoteException re){
				Log.e(TAG, "RemoteException", re);
			}
		}
	}

	@Override
	public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult){
	
		String authToken = null;
		try{
			authToken = accountManager.blockingGetAuthToken(account, Constants.AUTHTOKEN_TYPE, NOTIFY_AUTH_FAILURE);
			
			submitCompletedJobs(account, authToken, provider);
			
			syncJobsFromServer(account, authToken, provider);
			
			provider.release(); //do we need to do this?
		}catch(Exception e){
			handleException(e, authToken, syncResult);
		}
	}

	private synchronized void submitCompletedJobs(Account account, String authToken, ContentProviderClient provider) throws AuthenticatorException, IOException, RemoteException {
		Log.d(TAG, "submitCompletedJobs() called.");
		//Find which jobs have been completed.
		Cursor jobCursor = provider.query(Job.Definitions.CONTENT_URI, 
				new String[]{Job.Definitions._ID, Job.Definitions.TASK_DEFINITION_NAME}, 
				Job.Definitions.STATUS + " = ?", 
				new String[]{"COMPLETED"}, null);
		if(jobCursor != null){
			jobCursor.moveToFirst();
			//for each completed job:
			while(!jobCursor.isAfterLast()){
				Long jobId = jobCursor.getLong(0);
				Log.d(TAG, "creating submission for job " + jobId);
				
				Submission submission = new Submission();
//				submission.setId(jobId);
				submission.setJobId(jobId);
				submission.setTaskDefinitionName(jobCursor.getString(1));
				submission.setUsername(account.name);

				// retrieve dataitems for them and combine into a submission
				Cursor diCursor = provider.query(Dataitem.Definitions.CONTENT_URI, 
												 new String[]{Dataitem.Definitions.PAGENAME, Dataitem.Definitions.NAME, Dataitem.Definitions.TYPE, Dataitem.Definitions.VALUE}, 
												 Dataitem.Definitions.JOB_ID + " = ?", 
												 new String[]{""+jobId}, 
												 null);
				if(diCursor != null){
					diCursor.moveToFirst();
					while(!diCursor.isAfterLast()){
						DataItem dataItem = new DataItem(diCursor.getString(0),diCursor.getString(1),diCursor.getString(2),diCursor.getString(3));
//						Log.d(TAG, "Adding DataItem: " + dataItem);
						submission.addDataItem(dataItem);
						diCursor.moveToNext();
					}
					diCursor.close();
					diCursor = null;
				}

				//send completed job to server
				boolean submitted = NetworkUtilities.submitData(context, account, authToken, submission);
				//if successful, delete completed job and dataitems.
				if(submitted){
					Log.d(TAG, "Deleting old dataitems");
					provider.delete(Dataitem.Definitions.CONTENT_URI, Dataitem.Definitions.JOB_ID + " = ?", new String[]{""+submission.getJobId()});
					Log.d(TAG, "Deleting job: " + Uri.withAppendedPath(Job.Definitions.CONTENT_URI, ""+submission.getJobId()));
					provider.delete(Uri.withAppendedPath(Job.Definitions.CONTENT_URI, ""+submission.getJobId()), null, null);
				} else {
					Log.d(TAG, "Submitting job " + Uri.withAppendedPath(Job.Definitions.CONTENT_URI, ""+submission.getJobId()) + " failed.");
					throw new IOException("Submitting job " + Uri.withAppendedPath(Job.Definitions.CONTENT_URI, ""+submission.getJobId()) + " failed.");
				}
				jobCursor.moveToNext();
			}
			jobCursor.close();
			jobCursor = null;
		}
	}
	
	
	private synchronized void syncJobsFromServer(Account account, String authToken, ContentProviderClient provider) throws AuthenticatorException, IOException, RemoteException, AuthenticationException, ParseException, JSONException {
		//TODO: implement syncJobsFromServer();
		
		//get a list of jobs currently on the device (currentJobs)
		List<JobDefinition> currentJobs = new ArrayList<JobDefinition>();
		Cursor jobCursor = provider.query(Job.Definitions.CONTENT_URI, Job.Definitions.ALL, null, null, null);
		if(jobCursor != null){
			jobCursor.moveToFirst();
			while(!jobCursor.isAfterLast()){
				//int id, String name, TaskDefinition definition, Date created, Date due, String status, String notes, String group
				//_ID, NAME, TASK_DEFINITION_ID, CREATED, DUE, STATUS, GROUP, NOTES, MODIFIED
				JobDefinition job = new JobDefinition(jobCursor.getLong(0),
						jobCursor.getString(1),
						null, //do we need to retrieve the taskdefinition as well?
						new Date(jobCursor.getLong(4)),
						new Date(jobCursor.getLong(5)),
						jobCursor.getString(6),
						jobCursor.getString(7));

				currentJobs.add(job);
				jobCursor.moveToNext();
			}
			jobCursor.close();
			jobCursor = null;
		}
		//get list of jobs from server (newJobs)
		List<JobDefinition> newJobs = NetworkUtilities.fetchJobs(context, account, authToken, new Date(getLastUpdatedDate(account)));
		long newLastUpdated = System.currentTimeMillis();
		
		//delete any current jobs that aren't in the new jobs list (as this means they will have been completed elsewhere
		//TODO: Check this assumption - will need modifying if we switch to only sending new jobs, rather than all jobs
		if(currentJobs.size() > 0){
			Collection<JobDefinition> jobsToDelete = new ArrayList<JobDefinition>(currentJobs);
			jobsToDelete.removeAll(newJobs);
			for(JobDefinition jobToDelete: jobsToDelete){
				provider.delete(ContentUris.withAppendedId(Job.Definitions.CONTENT_URI, jobToDelete.getId()), null, null);
			}
		}
		
		//for each newJob:
		if(newJobs.size() > 0 ){
			for(JobDefinition newJob: newJobs){
				Log.d(TAG, "Adding a job to database: " + newJob);
				
				
				ContentValues values = new ContentValues();
				values.put(Job.Definitions._ID, newJob.getId());
				values.put(Job.Definitions.NAME, newJob.getName());
				values.put(Job.Definitions.TASK_DEFINITION_ID, newJob.getDefinition().getId());
				values.put(Job.Definitions.TASK_DEFINITION_NAME, newJob.getDefinition().getName());
				values.put(Job.Definitions.CREATED, newJob.getCreated().getTime());
				if(newJob.getDue() != null){
					values.put(Job.Definitions.DUE, newJob.getDue().getTime());
				} else {
					Log.e(TAG, "Job has no due date!");
				}
				values.put(Job.Definitions.STATUS, newJob.getStatus());
				values.put(Job.Definitions.NOTES, "null".equals(newJob.getNotes()) ? "" : newJob.getNotes());
				if(newJob.getGroup() != null){
					values.put(Job.Definitions.GROUP, newJob.getGroup());
				}
				
				Uri jobUri = ContentUris.withAppendedId(Job.Definitions.CONTENT_URI, newJob.getId());
				
				//  if job on device & device job isn't modified, then update job (to allow for changes from other sources)
				//  if job not on device, then create it on device
				boolean storeDataItems = false;
				if(currentJobs.contains(newJob) ){
					if(!currentJobs.get(currentJobs.indexOf(newJob)).isModified()){
						//update job
						Log.d(TAG, "Updating job " + newJob.getId());
						provider.update(jobUri, values, null, null);
						storeDataItems = true;
					}
				} else {
					storeTaskDefinition(provider, newJob.getDefinition());
					//create job
					Log.d(TAG, "Inserting new job " + newJob.getId());
					Uri providerUri = provider.insert(Job.Definitions.CONTENT_URI, values);
					Log.d(TAG, "Inserted job as " + providerUri);
					storeDataItems = true;
				}
				
				if(storeDataItems){
					for(DataItem item: newJob.getDataItems()){
						storeDataItem(provider, item, newJob);
					}
				}
			}
		}
		setLastUpdatedDate(account, newLastUpdated);
	}
	
	private void handleException(Exception e, String authtoken, SyncResult syncResult) {
	      if (e instanceof AuthenticatorException) {
	          syncResult.stats.numParseExceptions++;
	          Log.e(TAG, "AuthenticatorException", e);
	      } else if (e instanceof IOException) {
	          Log.e(TAG, "IOException", e);
	          syncResult.stats.numIoExceptions++;
	      } else if (e instanceof AuthenticationException) {
	          accountManager.invalidateAuthToken(AuthenticatorActivity.PARAM_AUTHTOKEN_TYPE, authtoken);
	          // The numAuthExceptions require user intervention and are
	          // considered hard errors.
	          // We automatically get a new hash, so let's make SyncManager retry
	          // automatically.
	          syncResult.stats.numIoExceptions++;
	          Log.e(TAG, "AuthenticationException", e);
//	      } else if (e instanceof ParseException) {
//	          syncResult.stats.numParseExceptions++;
//	          Log.e(TAG, "ParseException", e);
//	      } else if (e instanceof JsonParseException) {
//	          syncResult.stats.numParseExceptions++;
//	          Log.e(TAG, "JSONException", e);
	      } else if (e instanceof RemoteException){
	    	  syncResult.stats.numIoExceptions++;
	    	  Log.e(TAG, "RemoteException", e);
	      } else {
	    	  syncResult.stats.numIoExceptions++;
	    	  Log.e(TAG, "Unhandled Exception", e);
	      }
	  }
	
/*	
	public void onPerformSync(Account account, Bundle extras, String authority,
			ContentProviderClient provider, SyncResult syncResult) {

		Log.d(TAG, "onPerformSync() called.");
		List<JobDefinition> jobs = new ArrayList<JobDefinition>();
		List<TaskDefinition> definitions = new ArrayList<TaskDefinition>();
		String authToken = null;
		try{
			authToken = accountManager.blockingGetAuthToken(account, Constants.AUTHTOKEN_TYPE, NOTIFY_AUTH_FAILURE);
			long lastUpdated = getLastUpdatedDate(account);
			
			
			//Submit completed jobs
			//Find which jobs have been completed.
			Cursor jobCursor = provider.query(Job.Definitions.CONTENT_URI, Job.Definitions.ALL, Job.Definitions.STATUS + " = ?", new String[]{"COMPLETED"}, null);
			if(jobCursor != null){
				jobCursor.moveToFirst();
				//for each completed job:
				while(!jobCursor.isAfterLast()){
					
					
					int jobId = jobCursor.getInt(0);
					
					Submission submission = new Submission();
					submission.setJobId(jobId);
					submission.setUsername(account.name);

					// retrieve dataitems for them and combine into a submission
					Cursor diCursor = provider.query(Dataitem.Definitions.CONTENT_URI, 
													 new String[]{Dataitem.Definitions.PAGENAME, Dataitem.Definitions.NAME, Dataitem.Definitions.TYPE, Dataitem.Definitions.VALUE}, 
													 Dataitem.Definitions.JOB_ID + " = ?", 
													 new String[]{""+jobId}, 
													 null);
					if(diCursor != null){
						diCursor.moveToFirst();
						while(!diCursor.isAfterLast()){
							DataItem dataItem = new DataItem(diCursor.getString(0),diCursor.getString(1),diCursor.getString(2),diCursor.getString(3));
							Log.d(TAG, "Adding DataItem: " + dataItem);
							submission.addDataItem(dataItem);
							diCursor.moveToNext();
						}
						diCursor.close();
						diCursor = null;
					}

					//if successful, delete completed job and dataitems.
					boolean submitted = NetworkUtilities.submitData(context, account, authToken, submission);
					if(submitted){
						Log.d(TAG, "Deleting old dataitems");
						provider.delete(Dataitem.Definitions.CONTENT_URI, Dataitem.Definitions.JOB_ID + " = ?", new String[]{""+submission.getJobId()});
						Log.d(TAG, "Deleting job: " + Uri.withAppendedPath(Job.Definitions.CONTENT_URI, ""+submission.getJobId()));
						provider.delete(Uri.withAppendedPath(Job.Definitions.CONTENT_URI, ""+submission.getJobId()), null, null);
					}
					jobCursor.moveToNext();
				}
				
			}
			jobCursor.close();
			
			
			jobs = NetworkUtilities.fetchJobs(context, account, authToken, new Date(lastUpdated));
			//Commented out while developing/testing job synch
//			definitions = NetworkUtilities.fetchTaskDefinitions(account, authToken, new Date(lastUpdated));
			
			
			lastUpdated = System.currentTimeMillis(); //set to the current date/time
			//TODO: This should be handled by a TaskManager or similar really
			//for each TaskDefinition, we need to insert it into the SQLLite database
			for(TaskDefinition definition: definitions){
				storeTaskDefinition(provider, definition);
			}
			
			ArrayList<Long> newJobIds = new ArrayList<Long>();
			for(JobDefinition job: jobs){
				if(job != null){
					newJobIds.add(job.getId());
					
					Log.d(TAG, "Adding a job to database: " + job);
					storeTaskDefinition(provider, job.getDefinition());
					
					ContentValues values = new ContentValues();
					values.put(Job.Definitions._ID, job.getId());
					values.put(Job.Definitions.NAME, job.getName());
					values.put(Job.Definitions.TASK_DEFINITION_ID, job.getDefinition().getId());
					values.put(Job.Definitions.CREATED, job.getCreated().getTime());
					if(job.getDue() != null){
						values.put(Job.Definitions.DUE, job.getDue().getTime());
					} else {
						Log.e(TAG, "Job has no due date!");
					}
					values.put(Job.Definitions.STATUS, job.getStatus());
					values.put(Job.Definitions.NOTES, "null".equals(job.getNotes()) ? "" : job.getNotes());
					if(job.getGroup() != null){
						values.put(Job.Definitions.GROUP, job.getGroup());
					}
					
					Uri jobUri = ContentUris.withAppendedId(Job.Definitions.CONTENT_URI, job.getId());
	
					//TODO: If we have a job already on the device, but it doesn't come through in the list of newly synched jobs, then we want to remove it from the device.
					//This is because the job could have been completed on another device or by other means.
					
					//TODO: Stop fetched jobs blatting any saved jobs - particuarly the status - if the job has previously been completed.
					try{
						Cursor cursor = provider.query(jobUri, null, null, null, null);
						if(cursor.moveToFirst()){
							Log.d(TAG, "Updating job " + job.getId());
							values.remove(Job.Definitions.STATUS);
							provider.update(jobUri, values, null, null);
						} else {
							Log.d(TAG, "Inserting new job " + job.getId());
							Uri providerUri = provider.insert(Job.Definitions.CONTENT_URI, values);
							Log.d(TAG, "Inserted job as " + providerUri);
						}
						cursor.close();
						cursor = null;
					} catch(RemoteException re){
						Log.e(TAG, "RemoteException", re);
					}
					
					for(DataItem item: job.getDataItems()){
						storeDataItem(provider, item, job);
					}
				}
			}
			

			//Delete old jobs from the database.
			//Basically, anything that hasn't arrived in the recent sync.
//			Cursor cursor = provider.query(Job.Definitions.CONTENT_URI, new String[]{Job.Definitions._ID}, null, null, null);
//			if(cursor.moveToFirst()){
//				do{
//					int id = cursor.getInt(cursor.getColumnIndex(Job.Definitions._ID));
//					if(!newJobIds.contains(id)){
//						provider.delete(ContentUris.withAppendedId(Job.Definitions.CONTENT_URI, id), null, null);
//					}
//				}while(cursor.moveToNext());
//			}
//			cursor.close();
//			cursor = null;
//			
			setLastUpdatedDate(account, lastUpdated);
		}catch(final AuthenticatorException ae){
			syncResult.stats.numParseExceptions++;
			Log.e(TAG, "AuthenticatorException", ae);
		}catch(final AuthenticationException ae){
			accountManager.invalidateAuthToken(Constants.ACCOUNT_TYPE, authToken);
	        syncResult.stats.numAuthExceptions++;
			Log.e(TAG, "AuthenticationException", ae);
		}catch(final JSONException je){
			Log.e(TAG, "JSONException", je);
			syncResult.stats.numParseExceptions++;
		}catch(final OperationCanceledException oce){
			Log.e(TAG, "OperationCanceledException", oce);
		}catch(final IOException ioe){
			Log.e(TAG, "IOException", ioe);
			syncResult.stats.numIoExceptions++;
		} catch (RemoteException e) {
			Log.e(TAG, "RemoteException ", e);
		}
	}
*/
	private long getLastUpdatedDate(Account account){
		String lastUpdatedString = accountManager.getUserData(account, LAST_UPDATED_KEY);
		if(lastUpdatedString != null && lastUpdatedString.length() > 0){
			return Long.parseLong(lastUpdatedString);
		} else {
			return 0;
		}
		
	}
	
	private void setLastUpdatedDate(Account account, long lastUpdated){
		accountManager.setUserData(account, LAST_UPDATED_KEY, Long.toString(lastUpdated));
	}
}
