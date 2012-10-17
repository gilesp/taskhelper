package uk.co.vurt.hakken.syncadapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.auth.AuthenticationException;
import org.json.JSONException;

import uk.co.vurt.hakken.Constants;
import uk.co.vurt.hakken.authenticator.AuthenticatorActivity;
import uk.co.vurt.hakken.client.NetworkUtilities;
import uk.co.vurt.hakken.client.json.JobDefinitionHandler;
import uk.co.vurt.hakken.client.json.TaskDefinitionHandler;
import uk.co.vurt.hakken.domain.JSONUtil;
import uk.co.vurt.hakken.domain.job.DataItem;
import uk.co.vurt.hakken.domain.job.JobDefinition;
import uk.co.vurt.hakken.domain.job.Submission;
import uk.co.vurt.hakken.domain.job.SubmissionStatus;
import uk.co.vurt.hakken.domain.task.TaskDefinition;
import uk.co.vurt.hakken.processor.JobProcessor;
import uk.co.vurt.hakken.providers.Dataitem;
import uk.co.vurt.hakken.providers.Job;
import uk.co.vurt.hakken.providers.Task;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorException;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncResult;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.net.ParseException;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;

public class SyncAdapter extends AbstractThreadedSyncAdapter{
	private static final String TAG = "SyncAdapter";
	private static final String LAST_UPDATED_KEY = "uk.co.vurt.hakken.syncadapter.lastUpdated";
	private static final boolean NOTIFY_AUTH_FAILURE = true;
	
	private final AccountManager accountManager;
	private final Context context;

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
			}catch(SQLiteConstraintException e){
				try {
					//already present so do an update instead
					Cursor diCursor = provider.query(Dataitem.Definitions.CONTENT_URI, 
							new String[]{Dataitem.Definitions._ID,
							Dataitem.Definitions.VALUE}, 
							Dataitem.Definitions.PAGENAME + " = ?" +
							" AND " + Dataitem.Definitions.NAME + " = ?" + 
							" AND " + Dataitem.Definitions.TYPE + " = ?",
							new String[] {values.getAsString(Dataitem.Definitions.PAGENAME),
							values.getAsString(Dataitem.Definitions.NAME),
							values.getAsString(Dataitem.Definitions.TYPE)}, 
							Dataitem.Definitions.DEFAULT_SORT_ORDER);
					if(diCursor != null){
						diCursor.moveToFirst();
						//only update if the value has changed.
						if(!dataItem.getValue().equals(diCursor.getString(1))){
							ContentValues value = new ContentValues();
							value.put(Dataitem.Definitions.VALUE, dataItem.getValue());
							provider.update(ContentUris.withAppendedId(Dataitem.Definitions.CONTENT_URI, diCursor.getLong(0)), value, null, null);
						}
						diCursor.close();
						diCursor = null;
					}
				} catch (RemoteException re) {
					Log.e(TAG, "RemoteException", re);
				}
			}catch(RemoteException re){
				Log.e(TAG, "RemoteException", re);
			}
			values = null;
		}
	}

	@Override
	public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult){
	
		String authToken = null;
		try{
			authToken = accountManager.blockingGetAuthToken(account, Constants.AUTHTOKEN_TYPE, NOTIFY_AUTH_FAILURE);
			
			submitCompletedJobs(account, authToken, provider);

			syncTaskDefinitions(account, authToken, provider);
			
			syncJobsFromServer(account, authToken, provider);
			
		}catch(Exception e){
			handleException(e, authToken, syncResult);
		}
	}

	private synchronized void submitCompletedJobs(Account account, String authToken, ContentProviderClient provider) throws AuthenticatorException, IOException, RemoteException {
		Log.d(TAG, "submitCompletedJobs() called.");
		//Find which jobs have been completed.
		Cursor jobCursor = provider.query(Job.Definitions.CONTENT_URI, 
				new String[]{Job.Definitions._ID, Job.Definitions.REMOTE_ID, Job.Definitions.TASK_DEFINITION_ID}, 
				Job.Definitions.STATUS + " = ?", 
				new String[]{"COMPLETED"}, null);
		
		if(jobCursor != null){
			jobCursor.moveToFirst();
			//for each completed job:
			while(!jobCursor.isAfterLast()){
				Long jobId = jobCursor.getLong(0);
				Log.d(TAG, "creating submission for job " + jobId);
				
				Submission submission = new Submission();
				submission.setJobId(jobId);
				submission.setRemoteId(jobCursor.getString(1));
				
				String taskDefinitionName = "unknown";
				Cursor taskDefCursor = provider.query(Uri.withAppendedPath(Task.Definitions.CONTENT_URI, 
				        String.valueOf(jobCursor.getLong(2))), new String[]{Task.Definitions.NAME}, null, 
				        null, null);
				if(taskDefCursor != null){
				     taskDefCursor.moveToFirst();
				     taskDefinitionName = taskDefCursor.getString(0);
				}
				taskDefCursor.close();
				taskDefCursor = null;
				
				submission.setTaskDefinitionName(taskDefinitionName);

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
						submission.addDataItem(dataItem);
						diCursor.moveToNext();
					}
					diCursor.close();
					diCursor = null;
				}

				//send completed job to server
				SubmissionStatus status = NetworkUtilities.submitData(context, account, authToken, submission);
				//if successful, delete completed job and dataitems.
				if(status != null){
					if(status.isValid()){
						Log.d(TAG, "Deleting old dataitems");
						provider.delete(Dataitem.Definitions.CONTENT_URI, Dataitem.Definitions.JOB_ID + " = ?", new String[]{""+submission.getJobId()});
						Log.d(TAG, "Deleting job: " + Uri.withAppendedPath(Job.Definitions.CONTENT_URI, ""+submission.getJobId()));
						provider.delete(Uri.withAppendedPath(Job.Definitions.CONTENT_URI, ""+submission.getJobId()), null, null);
					} else {
						Log.d(TAG, "Submitting job " + Uri.withAppendedPath(Job.Definitions.CONTENT_URI, ""+submission.getJobId()) + " failed.");
						ContentValues values = new ContentValues();
						values.put(Job.Definitions.STATUS, "SERVER_ERROR");
						values.put(Job.Definitions.SERVER_ERROR, status.getMessage());
						provider.update(Uri.withAppendedPath(Job.Definitions.CONTENT_URI, ""+submission.getJobId()), values, null, null);
					}
				}
				jobCursor.moveToNext();
			}
			jobCursor.close();
			jobCursor = null;
		}
	}
	

	private synchronized void syncTaskDefinitions(Account account, String authToken, ContentProviderClient provider) throws AuthenticatorException, IOException, RemoteException, AuthenticationException, ParseException, JSONException {
		Log.d(TAG, "syncTaskDefintions() called.");
		
		// delete existing
		provider.delete(Task.Definitions.CONTENT_URI, null, null);
		
		//insert again
		TaskDefinitionAdapter taskAdapter = new TaskDefinitionAdapter(provider);		
		NetworkUtilities.fetchTaskDefinitions(context, account, authToken, new Date(getLastUpdatedDate(account)), taskAdapter);
	}
	
	private synchronized void syncJobsFromServer(Account account, String authToken, ContentProviderClient provider) throws AuthenticatorException, IOException, RemoteException, AuthenticationException, ParseException, JSONException {
		
		List<JobDefinitionId> oldJobIds = new ArrayList<JobDefinitionId>();
		
		Cursor jobCursor = provider.query(Job.Definitions.CONTENT_URI, Job.Definitions.ALL, null, null, null);
		if(jobCursor != null){
			jobCursor.moveToFirst();
			while(!jobCursor.isAfterLast()){

				boolean adhoc = jobCursor.getInt(JobProcessor.COLUMN_INDEX_JOB_ADHOC)>0;
				long oldJobId = jobCursor.getLong(JobProcessor.COLUMN_INDEX_JOB_ID);
				
				if(!adhoc){
					Log.d(TAG, "Marking old job: " + oldJobId);
	                oldJobIds.add(new JobDefinitionId(
	                        jobCursor.getLong(JobProcessor.COLUMN_INDEX_JOB_TASKDEFINITION_ID), 
	                        jobCursor.getString(JobProcessor.COLUMN_INDEX_JOB_REMOTE_ID)));
				}else {
					Log.d(TAG, "skipping adhoc job: " + oldJobId);
				}

				jobCursor.moveToNext();
			}
			jobCursor.close();
			jobCursor = null;
		}

		JobDefinitionAdapter jobAdapter = new JobDefinitionAdapter(provider);
		
		NetworkUtilities.fetchJobs(context,  account,  authToken,  new Date(getLastUpdatedDate(account)), jobAdapter);
		
		long newLastUpdated = System.currentTimeMillis();
		
		oldJobIds.removeAll(jobAdapter.getAddedJobIds());
		
		for(JobDefinitionId jobToDeleteId: oldJobIds){
//			provider.delete(ContentUris.withAppendedId(Job.Definitions.CONTENT_URI, jobToDeleteId), null, null);
		    
		    Log.d(TAG, "Deleting " + jobToDeleteId);
		    
		    provider.delete(Job.Definitions.CONTENT_URI, 
		            Job.Definitions.TASK_DEFINITION_ID + " = ? and " + Job.Definitions.REMOTE_ID + " = ?", 
		            new String[] {String.valueOf(jobToDeleteId.taskDefinitionId), jobToDeleteId.remoteId});	            
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
	      } else if (e instanceof RemoteException){
	    	  syncResult.stats.numIoExceptions++;
	    	  Log.e(TAG, "RemoteException", e);
	      } else {
	    	  syncResult.stats.numIoExceptions++;
	    	  Log.e(TAG, "Unhandled Exception", e);
	      }
	  }

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

	
	private class TaskDefinitionAdapter implements TaskDefinitionHandler {
		private ContentProviderClient provider;
		
		public TaskDefinitionAdapter(ContentProviderClient provider){
			this.provider = provider;
		}
				
		@Override
		public void handle(TaskDefinition newTask) {
			storeTaskDefinition(provider, newTask);
		}
	}
	
	
	private class JobDefinitionAdapter implements JobDefinitionHandler {

		private ContentProviderClient provider;
		private List<JobDefinitionId> addedJobIds;
		
		public JobDefinitionAdapter(ContentProviderClient provider){
			this.provider = provider;
					addedJobIds = new ArrayList<JobDefinitionId>();
		}
		
		@Override
		public void handle(JobDefinition job) {
			Log.d(TAG, "Adding a job to database: " + job);
			
			JobDefinitionId newJobId = new JobDefinitionId(job.getTaskDefinitionId(), job.getRemoteId()); 
			addedJobIds.add(newJobId);
			
			//retrieve job (if it exists)
			try {
				String whereClause = Job.Definitions.REMOTE_ID + " = ? AND " + Job.Definitions.TASK_DEFINITION_ID + " = ?";
				String[] whereArgs = new String[]{job.getRemoteId(), ""+job.getTaskDefinitionId()};
				Log.d(TAG, "RemoteID: " + job.getRemoteId() + " Task Def Id: " + job.getTaskDefinitionId());
				Cursor jobCursor = provider.query(Job.Definitions.CONTENT_URI, 
						new String[]{Job.Definitions._ID,Job.Definitions.MODIFIED, Job.Definitions.STATUS}, 
						whereClause, 
						whereArgs, null);
				
				ContentValues values = new ContentValues();
				values.put(Job.Definitions.REMOTE_ID, job.getRemoteId());
				values.put(Job.Definitions.NAME, job.getName());
				values.put(Job.Definitions.TASK_DEFINITION_ID, job.getTaskDefinitionId());
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
				
				//  if job on device & device job isn't modified, then update job (to allow for changes from other sources)
				//  if job not on device, then create it on device
				boolean storeDataItems = false;
				boolean resetStatus = false;
				long internalId = -1;
				Log.d(TAG, "jobCursor: " + jobCursor);
				Log.d(TAG, "count: " + jobCursor.getCount());
				if(jobCursor != null && jobCursor.getCount() > 0){
					// job exists, so check to see if we can update it.
					jobCursor.moveToFirst();
					Log.d(TAG, "_id: " + jobCursor.getLong(0));
					Log.d(TAG, "modified: " + jobCursor.getInt(1));
					Log.d(TAG, "status: " + jobCursor.getString(2));
					internalId = jobCursor.getLong(0);
					boolean modified = jobCursor.getInt(1)>0;
					String status = jobCursor.getString(2);
					jobCursor.close();
					jobCursor = null;
					
					if(!modified && !"SERVER_ERROR".equals(status)){
						//update job
						Log.d(TAG, "Updating job " + newJobId);
						values.put(Job.Definitions.STATUS, "UPDATING");
						
						int count = provider.update(ContentUris.withAppendedId(Job.Definitions.CONTENT_URI, internalId), values, whereClause, whereArgs);
						Log.d(TAG, "Updated " + count + " rows");
						resetStatus = true;
						storeDataItems = true;
					}
				
				} else {
					// job doesn't exist, so insert it.
					//storeTaskDefinition(provider, newJob.getDefinition());
					//create job
					Log.d(TAG, "Inserting new job " + newJobId);
					Uri providerUri = provider.insert(Job.Definitions.CONTENT_URI, values);
					Log.d(TAG, "Inserted job as " + providerUri);
					storeDataItems = true;
					
					// set the id to the last part of the provider uri
					// TODO: improve this
					Long jobId = Long.valueOf(providerUri.toString().substring(
                            providerUri.toString().lastIndexOf("/") + 1));
					Log.i(TAG, "New job id=" + jobId);
					internalId = jobId;
					job.setId(jobId);
				}
				
				if(storeDataItems){
					for(DataItem item: job.getDataItems()){
						storeDataItem(provider, item, job);
					}
					if(resetStatus){
						values = new ContentValues();
						values.put(Job.Definitions.STATUS, job.getStatus());
						provider.update(ContentUris.withAppendedId(Job.Definitions.CONTENT_URI, internalId), values, null, null);
					}
				}
			} catch (RemoteException re) {
				Log.e(TAG, "Unable to store job definition.", re);
			}
			
		}

		public List<JobDefinitionId> getAddedJobIds(){
			return addedJobIds;
		}
	}

	private static class JobDefinitionId {
	    
        private Long taskDefinitionId;
	    private String remoteId;
	    
	    JobDefinitionId(Long taskDefinitionId, String remoteId) {
	        this.taskDefinitionId = taskDefinitionId;
	        this.remoteId = remoteId;
	    }

        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((remoteId == null) ? 0 : remoteId.hashCode());
            result = prime * result
                    + ((taskDefinitionId == null) ? 0 : taskDefinitionId.hashCode());
            return result;
        }

        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            JobDefinitionId other = (JobDefinitionId) obj;
            if (remoteId == null) {
                if (other.remoteId != null)
                    return false;
            } else if (!remoteId.equals(other.remoteId))
                return false;
            if (taskDefinitionId == null) {
                if (other.taskDefinitionId != null)
                    return false;
            } else if (!taskDefinitionId.equals(other.taskDefinitionId))
                return false;
            return true;
        }
        
        public String toString() {
            return remoteId + " (taskDefinitionId " + taskDefinitionId + ")";
        }
	}
}
