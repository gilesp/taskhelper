package uk.co.vurt.hakken.providers;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

/**
 * TODO: Refactor this into something less verbose. DRY!
 * @author giles.paterson
 *
 */
public class TaskProvider extends ContentProvider {

	public static final String AUTHORITY = "uk.co.vurt.hakken";
	
	private static final String TAG = "TaskProvider";

	private static final String DATABASE_NAME = "tasks.db";
	private static final int DATABASE_VERSION = 16;
	private static final String DEFINITIONS_TABLE_NAME = "definitions";
	private static final String JOBS_TABLE_NAME = "jobs";
	private static final String DATAITEMS_TABLE_NAME = "data_items";
	
	private static HashMap<String, String> definitionsProjectionMap;
	private static HashMap<String, String> jobsProjectionMap;
	private static HashMap<String, String> dataitemsProjectionMap;
	
	private static final int DEFINITIONS_URI = 1;
	private static final int DEFINITION_ID_URI = 2;
	private static final int JOBS_URI = 3;
	private static final int JOB_ID_URI = 4;
	private static final int DATAITEMS_URI = 5;
	private static final int DATAITEM_ID_URI = 6;
	
	private static final UriMatcher uriMatcher;
	private DatabaseHelper dbHelper;

	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		
		uriMatcher.addURI(AUTHORITY, Task.Definitions.PATH, DEFINITIONS_URI);
		uriMatcher.addURI(AUTHORITY, Task.Definitions.PATH + "/#", DEFINITION_ID_URI);
		uriMatcher.addURI(AUTHORITY, Job.Definitions.PATH, JOBS_URI);
		uriMatcher.addURI(AUTHORITY, Job.Definitions.PATH + "/#", JOB_ID_URI);
		uriMatcher.addURI(AUTHORITY, Dataitem.Definitions.PATH, DATAITEMS_URI);
		uriMatcher.addURI(AUTHORITY, Dataitem.Definitions.PATH + "/#", DATAITEM_ID_URI);
		
		definitionsProjectionMap = new HashMap<String, String>();
		definitionsProjectionMap.put(Task.Definitions._ID, Task.Definitions._ID);
		definitionsProjectionMap.put(Task.Definitions.NAME, Task.Definitions.NAME);
		definitionsProjectionMap.put(Task.Definitions.DESCRIPTION, Task.Definitions.DESCRIPTION);
		definitionsProjectionMap.put(Task.Definitions.JSON, Task.Definitions.JSON);
		
		jobsProjectionMap = new HashMap<String, String>();
		jobsProjectionMap.put(Job.Definitions._ID, Job.Definitions._ID);
		jobsProjectionMap.put(Job.Definitions.NAME, Job.Definitions.NAME);
		jobsProjectionMap.put(Job.Definitions.TASK_DEFINITION_ID, Job.Definitions.TASK_DEFINITION_ID);
		jobsProjectionMap.put(Job.Definitions.TASK_DEFINITION_NAME, Job.Definitions.TASK_DEFINITION_NAME);
		jobsProjectionMap.put(Job.Definitions.CREATED, Job.Definitions.CREATED);
		jobsProjectionMap.put(Job.Definitions.DUE, Job.Definitions.DUE);
		jobsProjectionMap.put(Job.Definitions.STATUS, Job.Definitions.STATUS);
		jobsProjectionMap.put(Job.Definitions.GROUP, Job.Definitions.GROUP);
		jobsProjectionMap.put(Job.Definitions.NOTES, Job.Definitions.NOTES);
		jobsProjectionMap.put(Job.Definitions.MODIFIED, Job.Definitions.MODIFIED);

		dataitemsProjectionMap = new HashMap<String, String>();
		dataitemsProjectionMap.put(Dataitem.Definitions._ID, Dataitem.Definitions._ID);
		dataitemsProjectionMap.put(Dataitem.Definitions.JOB_ID, Dataitem.Definitions.JOB_ID);
		dataitemsProjectionMap.put(Dataitem.Definitions.PAGENAME, Dataitem.Definitions.PAGENAME);
		dataitemsProjectionMap.put(Dataitem.Definitions.NAME, Dataitem.Definitions.NAME);
		dataitemsProjectionMap.put(Dataitem.Definitions.TYPE, Dataitem.Definitions.TYPE);
		dataitemsProjectionMap.put(Dataitem.Definitions.VALUE, Dataitem.Definitions.VALUE);
	}

	@Override
	public int delete(Uri uri, String whereClause, String[] whereArgs) {
		Log.d(TAG, "Delete requested for " + uri + " " + whereClause + ":" + whereArgs);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		int count = 0;
		String tableName = null;
		switch (uriMatcher.match(uri)) {
			case DEFINITIONS_URI:
				tableName = DEFINITIONS_TABLE_NAME;
//				count = db.delete(DEFINITIONS_TABLE_NAME, whereClause, whereArgs);
				break;
			case DEFINITION_ID_URI:
				tableName = DEFINITIONS_TABLE_NAME;
				whereClause = Task.Definitions._ID + "=?";
				whereArgs = new String[]{uri.getPathSegments().get(1)};
//				String definitionId = uri.getPathSegments().get(1);
//				count = db.delete(DEFINITIONS_TABLE_NAME, Task.Definitions._ID
//						+ "=?"
//						/*+ definitionId
//						+ (!TextUtils.isEmpty(whereClause) ? " AND (" + whereClause
//								+ ')' : "")*/, 
//						new String[]{definitionId});
				break;
			case JOBS_URI:
				tableName = JOBS_TABLE_NAME;
//				count = db.delete(JOBS_TABLE_NAME, whereClause, whereArgs);
				break;
			case JOB_ID_URI:
				String jobId = uri.getPathSegments().get(1);
				
				//we need to delete all dataitems associated with this job, before deleting the job itself
				Cursor diCursor = query(Dataitem.Definitions.CONTENT_URI, 
						 new String[]{Dataitem.Definitions._ID}, 
						 Dataitem.Definitions.JOB_ID + " = ?", 
						 new String[]{""+jobId}, 
						 null);
				if(diCursor != null){
					diCursor.moveToFirst();
					while(!diCursor.isAfterLast()){
						delete(Uri.withAppendedPath(Dataitem.Definitions.CONTENT_URI, ""+diCursor.getLong(0)), null, null);
						diCursor.moveToNext();
					}
					diCursor.close();
					diCursor = null;
				}
				
				tableName = JOBS_TABLE_NAME;
				whereClause = Job.Definitions._ID + "=?";
				whereArgs = new String[]{jobId};
				
//				count = db.delete(JOBS_TABLE_NAME, Job.Definitions._ID
//						+ "=?",
//						new String[]{jobId});
				break;
			case DATAITEMS_URI:
				tableName = DATAITEMS_TABLE_NAME;
//				count = db.delete(DATAITEMS_TABLE_NAME, whereClause, whereArgs);
				break;
			case DATAITEM_ID_URI:
				tableName = DATAITEMS_TABLE_NAME;
				whereClause = Dataitem.Definitions._ID + "=?";
				whereArgs = new String[]{uri.getPathSegments().get(1)};
				
//				String dataitemId = uri.getPathSegments().get(1);
//				count = db.delete(DATAITEMS_TABLE_NAME, 
//						Dataitem.Definitions._ID + "=?",
//						new String[]{dataitemId});
				break;
			default:
				throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		
		if(tableName != null){
			count = db.delete(tableName, whereClause, whereArgs);
		}
		
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	@Override
	public String getType(Uri uri) {
		switch (uriMatcher.match(uri)) {
			case DEFINITIONS_URI:
				return Task.Definitions.CONTENT_TYPE;
			case DEFINITION_ID_URI:
				return Task.Definitions.CONTENT_ITEM_TYPE;
			case JOBS_URI:
				return Job.Definitions.CONTENT_TYPE;
			case JOB_ID_URI:
				return Job.Definitions.CONTENT_ITEM_TYPE;
			case DATAITEMS_URI:
				return Dataitem.Definitions.CONTENT_TYPE;
			case DATAITEM_ID_URI:
				return Dataitem.Definitions.CONTENT_ITEM_TYPE;
			default:
				throw new IllegalArgumentException("Unknown URI: " + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues initialValues) {
		ContentValues values;
		Log.d(TAG, "Insert requested URI: " + uri);
		
		if (initialValues != null) {
			values = new ContentValues(initialValues);
			Log.d(TAG, "Using initial values");
		} else {
			values = new ContentValues();
			Log.d(TAG, "Using empty contentvalues");
		}
		
		switch (uriMatcher.match(uri)){
			case DEFINITIONS_URI:
				Log.d(TAG, "Processing Task Definition");
//				if (initialValues != null) {
//					values = new ContentValues(initialValues);
//					Log.d(TAG, "Using initial values for Task Definition");
//				} else {
//					values = new ContentValues();
//					Log.d(TAG, "Using empty contentvalues for Task Definition");
//				}

				// Ensure all values are set
				if (values.containsKey(Task.Definitions.NAME)
						&& values.containsKey(Task.Definitions.DESCRIPTION)
						&& values.containsKey(Task.Definitions._ID)) {

					SQLiteDatabase db = dbHelper.getWritableDatabase();
					long rowId = db.insert(DEFINITIONS_TABLE_NAME, Task.Definitions.NAME,
							values);
					if (rowId > 0) {
						Uri definitionUri = ContentUris.withAppendedId(
								Task.Definitions.CONTENT_URI, rowId);
						getContext().getContentResolver().notifyChange(definitionUri,
								null, false);
						return definitionUri;
					}
				}else {
					Log.d(TAG, "Missing value.");
					Log.d(TAG, "Name: " + values.containsKey(Task.Definitions.NAME));
					Log.d(TAG, "Description: " + values.containsKey(Task.Definitions.DESCRIPTION));
					Log.d(TAG, "Id: " + values.containsKey(Task.Definitions._ID));
					Set<Entry<String,Object>> valueSet = values.valueSet();
					for(Entry<String,Object> entry: valueSet){
						Log.d(TAG, "KEY: " + entry.getKey() + " VALUE: " + entry.getValue());
					}
					throw new SQLException("Unable to insert row into " + uri);
				}
				break;
			case JOBS_URI:
				Log.d(TAG, "Processing Job");
//				if(initialValues != null){
//					values = new ContentValues(initialValues);
//					Log.d(TAG, "Using initial values for Job");
//				}else {
//					values = new ContentValues();
//					Log.d(TAG, "Using empty contentvalues for Job");
//				}
//				
				if(values.containsKey(Job.Definitions.NAME) 
						&& values.containsKey(Job.Definitions.TASK_DEFINITION_ID)
						&& values.containsKey(Job.Definitions.CREATED)
						&& values.containsKey(Job.Definitions.DUE)
						&& values.containsKey(Job.Definitions.STATUS)
						&& values.containsKey(Job.Definitions._ID)){
					SQLiteDatabase db = dbHelper.getWritableDatabase();
					long rowId = db.insert(JOBS_TABLE_NAME, Job.Definitions.NAME, values);
					if(rowId > 0){
						Uri jobUri = ContentUris.withAppendedId(Job.Definitions.CONTENT_URI, rowId);
						getContext().getContentResolver().notifyChange(jobUri, null, false);
						return jobUri;
					}
				} else {
					Log.d(TAG, "Missing value.");
					Log.d(TAG, "Id: " + values.containsKey(Task.Definitions._ID));
					Log.d(TAG, "Name: " + values.containsKey(Job.Definitions.NAME));
					Log.d(TAG, "Definition ID: " + values.containsKey(Job.Definitions.TASK_DEFINITION_ID));
					Log.d(TAG, "Created: " + values.containsKey(Job.Definitions.CREATED));
					Log.d(TAG, "Due: " + values.containsKey(Job.Definitions.DUE));
					Log.d(TAG, "Status: " + values.containsKey(Job.Definitions.STATUS));
					Log.d(TAG, "Group: " + values.containsKey(Job.Definitions.GROUP));
					throw new SQLException("Unable to insert row into " + uri);
				}
				break;
			case DATAITEMS_URI:
				Log.d(TAG, "Processing Dataitem");
				if(values.containsKey(Dataitem.Definitions.JOB_ID)
						&& values.containsKey(Dataitem.Definitions.PAGENAME)
						&& values.containsKey(Dataitem.Definitions.NAME)
						&& values.containsKey(Dataitem.Definitions.TYPE)
						&& values.containsKey(Dataitem.Definitions.VALUE)){
					SQLiteDatabase db = dbHelper.getWritableDatabase();
					long rowId = db.insert(DATAITEMS_TABLE_NAME, Dataitem.Definitions.NAME, values);
					if(rowId > 0){
						Uri dataitemUri = ContentUris.withAppendedId(Dataitem.Definitions.CONTENT_URI, rowId);
						Log.d(TAG, "Saved dataitem " + dataitemUri);
						getContext().getContentResolver().notifyChange(dataitemUri, null, false);
						return dataitemUri;
					} else {
						Log.d(TAG, "Dataitem not saved.");
					}
				} else {
					Log.d(TAG, "Missing value.");
					Log.d(TAG, "Job Id: " + values.containsKey(Dataitem.Definitions.JOB_ID));
					Log.d(TAG, "Page name: " + values.containsKey(Dataitem.Definitions.PAGENAME));
					Log.d(TAG, "Name: " + values.containsKey(Dataitem.Definitions.NAME));
					Log.d(TAG, "Type: " + values.containsKey(Dataitem.Definitions.TYPE));
					Log.d(TAG, "Value: " + values.containsKey(Dataitem.Definitions.VALUE));
					throw new SQLException("Unable to insert row into " + uri);
				}
			default:
				throw new IllegalArgumentException("Unknown URI: " + uri + " MAtched value: " + uriMatcher.match(uri));
		}
		return null;
	}

	@Override
	public boolean onCreate() {
		dbHelper = new DatabaseHelper(getContext());
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		
		String orderBy;
		String groupBy = null;
		switch (uriMatcher.match(uri)) {
			case DEFINITIONS_URI:
				queryBuilder.setTables(DEFINITIONS_TABLE_NAME);
				queryBuilder.setProjectionMap(definitionsProjectionMap);
				orderBy = Task.Definitions.DEFAULT_SORT_ORDER;
				break;
			case DEFINITION_ID_URI:
				queryBuilder.setTables(DEFINITIONS_TABLE_NAME);
				queryBuilder.setProjectionMap(definitionsProjectionMap);
				orderBy = Task.Definitions.DEFAULT_SORT_ORDER;
				queryBuilder.appendWhere(Task.Definitions._ID + "="
						+ uri.getPathSegments().get(1));
				break;
			case JOBS_URI:
				queryBuilder.setTables(JOBS_TABLE_NAME);
				queryBuilder.setProjectionMap(jobsProjectionMap);
				orderBy = Job.Definitions.DEFAULT_SORT_ORDER;
//				groupBy = Job.Definitions.GROUP;
				break;
			case JOB_ID_URI:
				queryBuilder.setTables(JOBS_TABLE_NAME);
				queryBuilder.setProjectionMap(jobsProjectionMap);
				orderBy = Job.Definitions.DEFAULT_SORT_ORDER;
				queryBuilder.appendWhere(Job.Definitions._ID + "=" + uri.getPathSegments().get(1));
				break;
			case DATAITEMS_URI:
				queryBuilder.setTables(DATAITEMS_TABLE_NAME);
				queryBuilder.setProjectionMap(dataitemsProjectionMap);
				orderBy = Dataitem.Definitions.DEFAULT_SORT_ORDER;
				break;
			case DATAITEM_ID_URI:
				queryBuilder.setTables(DATAITEMS_TABLE_NAME);
				queryBuilder.setProjectionMap(dataitemsProjectionMap);
				orderBy = Dataitem.Definitions.DEFAULT_SORT_ORDER;
				queryBuilder.appendWhere(Dataitem.Definitions._ID + "=" + uri.getPathSegments().get(1));
				break;
			default:
				throw new IllegalArgumentException("Unknown URI: " + uri);
		}

		// Use specified sort order, if provided
		if (!TextUtils.isEmpty(sortOrder)) {
			orderBy = sortOrder;
		}

		// Get the database and run the query
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = queryBuilder.query(db, projection, selection,
				selectionArgs, groupBy, null, orderBy);

		// Tell the cursor what uri to watch, so it knows when its source data
		// changes
		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		return cursor;
	}

	@Override
	public int update(Uri uri, ContentValues values, String whereClause, String[] whereArgs) {
		Log.d(TAG, "Update requested " + uri);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
        int count;
        boolean syncToNetwork = false;
        switch (uriMatcher.match(uri)) {
	        case DEFINITIONS_URI:
	            count = db.update(DEFINITIONS_TABLE_NAME, values, whereClause, whereArgs);
	            break;
	        case DEFINITION_ID_URI:
	            count = db.update(DEFINITIONS_TABLE_NAME, values, Task.Definitions._ID + "=" + uri.getPathSegments().get(1)
	                    + (!TextUtils.isEmpty(whereClause) ? " AND (" + whereClause + ')' : ""), whereArgs);
	            break;
	        case JOBS_URI:
	            count = db.update(JOBS_TABLE_NAME, values, whereClause, whereArgs);
	            break;
	        case JOB_ID_URI:
	            count = db.update(JOBS_TABLE_NAME, values, Job.Definitions._ID + "=" + uri.getPathSegments().get(1)
	                    + (!TextUtils.isEmpty(whereClause) ? " AND (" + whereClause + ')' : ""), whereArgs);
	            break;
	        case DATAITEMS_URI:
	            count = db.update(DATAITEMS_TABLE_NAME, values, whereClause, whereArgs);
	            if(values.containsKey(Job.Definitions.MODIFIED)){
	            	syncToNetwork = true;
	            }
	            break;
	        case DATAITEM_ID_URI:
	            count = db.update(DATAITEMS_TABLE_NAME, values, Dataitem.Definitions._ID + "=" + uri.getPathSegments().get(1)
	                    + (!TextUtils.isEmpty(whereClause) ? " AND (" + whereClause + ')' : ""), whereArgs);
//	            syncToNetwork = true;
	            break;
	        default:
	            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null, syncToNetwork);
        return count;
	}

	/**
	 * This class helps open, create, and upgrade the database file.
	 */
	private static class DatabaseHelper extends SQLiteOpenHelper {

		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			//Create Task Definitions table
			db.execSQL("CREATE TABLE IF NOT EXISTS " + DEFINITIONS_TABLE_NAME + " ("
					+ Task.Definitions._ID + " INTEGER PRIMARY KEY,"
					+ Task.Definitions.NAME + " TEXT," 
					+ Task.Definitions.DESCRIPTION + " TEXT,"
					+ Task.Definitions.JSON + " TEXT"
					+ ");");
			
			//Create Jobs table
			db.execSQL("CREATE TABLE IF NOT EXISTS " + JOBS_TABLE_NAME + " ("
					+ Job.Definitions._ID + " INTEGER PRIMARY KEY, "
					+ Job.Definitions.NAME + " TEXT, "
					+ Job.Definitions.TASK_DEFINITION_ID + " INTEGER REFERENCES " + DEFINITIONS_TABLE_NAME + " (" + Task.Definitions._ID + "), "
					+ Job.Definitions.TASK_DEFINITION_NAME + " TEXT, "
					+ Job.Definitions.CREATED + " INTEGER, "
					+ Job.Definitions.DUE + " INTEGER, "
					+ Job.Definitions.NOTES + " TEXT, "
					+ Job.Definitions.GROUP + " TEXT DEFAULT 'Personal', "
					+ Job.Definitions.STATUS + " TEXT, " 
					+ Job.Definitions.MODIFIED + " INTEGER DEFAULT 0 "
					+ ");");
			
			//Create dataitems table
			db.execSQL("CREATE TABLE IF NOT EXISTS " + DATAITEMS_TABLE_NAME + " ("
					+ Dataitem.Definitions._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
					+ Dataitem.Definitions.JOB_ID + " INTEGER REFERENCES " + JOBS_TABLE_NAME + " (" + Job.Definitions._ID + "), "
					+ Dataitem.Definitions.PAGENAME + " TEXT, "
					+ Dataitem.Definitions.NAME + " TEXT, "
					+ Dataitem.Definitions.TYPE + " TEXT, "
					+ Dataitem.Definitions.VALUE + " TEXT, "
					+ "UNIQUE (" + Dataitem.Definitions.JOB_ID + "," + Dataitem.Definitions.PAGENAME + "," + Dataitem.Definitions.NAME + "," + Dataitem.Definitions.TYPE + ")"
					+ ");");
		}

		@Override
		/*
		 * Realistically, we'd want to only add to the database table when
		 * upgrading, in order to preserve existing data
		 */
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
					+ newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS " + DEFINITIONS_TABLE_NAME);
			db.execSQL("DROP TABLE IF EXISTS " + JOBS_TABLE_NAME);
			db.execSQL("DROP TABLE IF EXISTS " + DATAITEMS_TABLE_NAME);
			onCreate(db);
		}
	}

}
