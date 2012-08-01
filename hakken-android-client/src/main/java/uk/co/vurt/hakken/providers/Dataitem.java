package uk.co.vurt.hakken.providers;

import android.net.Uri;
import android.provider.BaseColumns;

public class Dataitem {

	private Dataitem(){}
	
	public final static class Definitions implements BaseColumns {
		private Definitions(){}
		
		public static final String PATH = "dataitem";
		
		public static final Uri CONTENT_URI = Uri.parse("content://" + TaskProvider.AUTHORITY + "/" + PATH);
		
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.vurt.hakken.dataitem";
		
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.vurt.hakken.dataitem";
		
		public static final String JOB_ID = "jobid";
		
		public static final String PAGENAME = "pagename";
		
		public static final String NAME = "name";
		
		public static final String TYPE = "type";
		
		public static final String VALUE = "value";
		
		public static final String DEFAULT_SORT_ORDER = NAME + " ASC";
	}
	
	public final static class Instance implements BaseColumns {
		
	}
}
