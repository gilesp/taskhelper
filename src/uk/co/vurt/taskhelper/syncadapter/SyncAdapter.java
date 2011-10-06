package uk.co.vurt.taskhelper.syncadapter;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.http.auth.AuthenticationException;
import org.json.JSONException;

import uk.co.vurt.taskhelper.Constants;
import uk.co.vurt.taskhelper.client.Job;
import uk.co.vurt.taskhelper.client.NetworkUtilities;
import uk.co.vurt.taskhelper.client.TaskDefinition;
import uk.co.vurt.taskhelper.providers.Task;
import uk.co.vurt.taskhelper.providers.TaskProvider;
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
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;

public class SyncAdapter extends AbstractThreadedSyncAdapter {
	private static final String TAG = "SyncAdapter";
	
	private final AccountManager accountManager;
	private final Context context;
	
	private Date lastUpdated;
	
	public SyncAdapter(Context context, boolean autoInitialize) {
		super(context, autoInitialize);
		this.context = context;
		accountManager = AccountManager.get(context);
	}

	@Override
	public void onPerformSync(Account account, Bundle extras, String authority,
			ContentProviderClient provider, SyncResult syncResult) {

		List<Job> jobs;
		List<TaskDefinition> definitions;
		String authToken = null;
		try{
			authToken = accountManager.blockingGetAuthToken(account, Constants.AUTHTOKEN_TYPE, true);
			//jobs = NetworkUtilities.fetchJobs(account, authToken, lastUpdated);
			definitions = NetworkUtilities.fetchTaskDefinitions(account, authToken, lastUpdated);
			lastUpdated = new Date();
			//TODO: This should be handled by a TaskManager or similar really
			//for each TaskDefinition, we need to insert it into the SQLLite database
			for(TaskDefinition definition: definitions){
				Log.d(TAG, "Adding a definition to database: " + definition);
				if(definition != null){
					ContentValues values = new ContentValues();
					values.put(Task.Definitions._ID, definition.getId());
					values.put(Task.Definitions.NAME, definition.getName());
					values.put(Task.Definitions.DESCRIPTION, definition.getDescription());
					
					//TODO: Loop through each page, adding it to a pages table
					//values.put(Task.Definitions.PAGES, definition.getPages());
					
					//We need to see if the definition already exists, if it does do an update otherwise do an insert
					Uri definitionUri = ContentUris.withAppendedId(Task.Definitions.CONTENT_URI, definition.getId());
					
					try {
						Cursor cur = provider.query(definitionUri, null, null, null, null);
						if(cur.moveToFirst()){
							Log.d(TAG, "Updating task definition " + definition.getId());
							provider.update(definitionUri, values, null, null);
						}else{
							Log.d(TAG, "Inserting new definition " + definition.getId());
							provider.insert(Task.Definitions.CONTENT_URI, values);
						}
						cur.close();
						cur = null;
					} catch (RemoteException re) {
						Log.e(TAG, "RemoteException", re);
					}
				}else{
					Log.d(TAG, "Null definition");
				}
			}
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
		}
	}

}
