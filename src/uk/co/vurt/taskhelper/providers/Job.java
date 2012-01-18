package uk.co.vurt.taskhelper.providers;

import android.net.Uri;
import android.provider.BaseColumns;

public class Job {

//	public final static String AUTHORITY = "uk.co.vurt.taskhelper";
	
	/* Don't insantiate this class. */
	private Job(){}
	
	public final static class Definitions implements BaseColumns {
		private Definitions(){}
		
		public static final String PATH = "job";
		
		public static final Uri CONTENT_URI = Uri.parse("content://" + TaskProvider.AUTHORITY + "/" + PATH);
		
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.vurt.taskhelper.job";
		
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.vurt.taskhelper.job";
		
		public static final String NAME = "name";
		
		public static final String TASK_DEFINITION_ID = "task_definition_id";
		
		public static final String CREATED = "created";
		public static final String DUE = "due";
		public static final String STATUS = "status";
		public static final String GROUP = "groupname";
		
		public static final String DEFAULT_SORT_ORDER = GROUP + ", " + DUE + " ASC";
		
		public static final String[] ALL = new String[]{
			_ID,
			NAME,
			TASK_DEFINITION_ID,
			CREATED,
			DUE,
			STATUS,
			GROUP
		};
	}
	
	public final static class Instance implements BaseColumns{
		
	}
}
