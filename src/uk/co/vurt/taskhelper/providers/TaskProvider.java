package uk.co.vurt.taskhelper.providers;

import java.util.HashMap;

import uk.co.vurt.taskhelper.providers.Task.Definitions;
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

public class TaskProvider extends ContentProvider {

	private static final String TAG = "TaskProvider";

	private static final String DATABASE_NAME = "task.db";
	private static final int DATABASE_VERSION = 1;
	private static final String DEFINITIONS_TABLE_NAME = "definitions";
	private static final String DEFINITION_PAGES_TABLE_NAME = "definitionPages";

	private static HashMap<String, String> definitionsProjectionMap;
	private static HashMap<String, String> pagesProjectionMap;
	
	private static final int DEFINITIONS = 1;
	private static final int DEFINITION_ID = 2;
	private static final int PAGES = 3;
	private static final int PAGE_ID = 4;
	
	private static final UriMatcher uriMatcher;
	private DatabaseHelper dbHelper;

	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(Task.AUTHORITY, Task.Definitions.PATH, DEFINITIONS);
		uriMatcher.addURI(Task.AUTHORITY, Task.Definitions.PATH + "/#", DEFINITION_ID);

		definitionsProjectionMap = new HashMap<String, String>();
		definitionsProjectionMap.put(Definitions._ID, Definitions._ID);
		definitionsProjectionMap.put(Definitions.NAME, Definitions.NAME);
		definitionsProjectionMap.put(Definitions.DESCRIPTION, Definitions.DESCRIPTION);
		
		pagesProjectionMap = new HashMap<String, String>();
//		pagesProjectionMap.put(Pages._ID, Pages._ID);
//		pagesProjectionMap.put(Pages.DEFINITION_ID, Pages.DEFINITION_ID);
	}

	@Override
	public int delete(Uri uri, String whereClause, String[] whereArgs) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		int count;
		switch (uriMatcher.match(uri)) {
			case DEFINITIONS:
				count = db.delete(DEFINITIONS_TABLE_NAME, whereClause, whereArgs);
				break;
			case DEFINITION_ID:
				String definitionId = uri.getPathSegments().get(1);
				count = db.delete(DEFINITIONS_TABLE_NAME, Definitions._ID
						+ "="
						+ definitionId
						+ (!TextUtils.isEmpty(whereClause) ? " AND (" + whereClause
								+ ')' : ""), whereArgs);
				break;
			default:
				throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	@Override
	public String getType(Uri uri) {
		switch (uriMatcher.match(uri)) {
			case DEFINITIONS:
				return Definitions.CONTENT_TYPE;
			case DEFINITION_ID:
				return Definitions.CONTENT_ITEM_TYPE;
			default:
				throw new IllegalArgumentException("Unknown URI: " + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues initialValues) {
		if (uriMatcher.match(uri) != DEFINITIONS) {
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}

		ContentValues values;
		if (initialValues != null) {
			values = new ContentValues(initialValues);
			Log.d(TAG, "Using initial values");
		} else {
			values = new ContentValues();
			Log.d(TAG, "Using empty contentvalues");
		}

		// Ensure all values are set
		if (values.containsKey(Definitions.NAME)
				&& values.containsKey(Definitions.DESCRIPTION)
				&& values.containsKey(Definitions._ID)) {

			SQLiteDatabase db = dbHelper.getWritableDatabase();
			long rowId = db.insert(DEFINITIONS_TABLE_NAME, Definitions.NAME,
					values);
			if (rowId > 0) {
				Uri definitionUri = ContentUris.withAppendedId(
						Definitions.CONTENT_URI, rowId);
				getContext().getContentResolver().notifyChange(definitionUri,
						null);
				return definitionUri;
			}
		}else {
			Log.d(TAG, "Missing value.");
			Log.d(TAG, "Name: " + values.containsKey(Definitions.NAME));
			Log.d(TAG, "Description: " + values.containsKey(Definitions.DESCRIPTION));
			Log.d(TAG, "Id: " + values.containsKey(Definitions._ID));
		}

		throw new SQLException("Unable to insert row into " + uri);
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
		queryBuilder.setTables(DEFINITIONS_TABLE_NAME);

		switch (uriMatcher.match(uri)) {
			case DEFINITIONS:
				queryBuilder.setProjectionMap(definitionsProjectionMap);
				break;
			case DEFINITION_ID:
				queryBuilder.setProjectionMap(definitionsProjectionMap);
				queryBuilder.appendWhere(Definitions._ID + "="
						+ uri.getPathSegments().get(1));
				break;
			default:
				throw new IllegalArgumentException("Unknown URI: " + uri);
		}

		// If no sort order is specified use the default
		String orderBy;
		if (TextUtils.isEmpty(sortOrder)) {
			orderBy = Definitions.DEFAULT_SORT_ORDER;
		} else {
			orderBy = sortOrder;
		}

		// Get the database and run the query
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = queryBuilder.query(db, projection, selection,
				selectionArgs, null, null, orderBy);

		// Tell the cursor what uri to watch, so it knows when its source data
		// changes
		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		return cursor;
	}

	@Override
	public int update(Uri uri, ContentValues values, String whereClause, String[] whereArgs) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
        int count;
        switch (uriMatcher.match(uri)) {
	        case DEFINITIONS:
	            count = db.update(DEFINITIONS_TABLE_NAME, values, whereClause, whereArgs);
	            break;
	
	        case DEFINITION_ID:
	            String noteId = uri.getPathSegments().get(1);
	            count = db.update(DEFINITIONS_TABLE_NAME, values, Definitions._ID + "=" + noteId
	                    + (!TextUtils.isEmpty(whereClause) ? " AND (" + whereClause + ')' : ""), whereArgs);
	            break;
	
	        default:
	            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
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
			db.execSQL("CREATE TABLE " + DEFINITIONS_TABLE_NAME + " ("
					+ Definitions._ID + " INTEGER PRIMARY KEY,"
					+ Definitions.NAME + " TEXT," + Definitions.DESCRIPTION
					+ " TEXT" + ");");
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
			onCreate(db);
		}
	}
}
