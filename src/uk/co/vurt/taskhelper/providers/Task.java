package uk.co.vurt.taskhelper.providers;

import android.net.Uri;
import android.provider.BaseColumns;

public final class Task {

	public final static String AUTHORITY = "uk.co.vurt.taskhelper";
	
	/* Don't instantiate this class. */
	private Task(){}
	
	public final static class Definitions implements BaseColumns {
		private Definitions(){}
		
		public static final String PATH = "definition";
		
		/**
         * The content:// style URL for this table
         */
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + PATH);
        
        /**
         * The MIME type of {@link #CONTENT_URI} providing a directory of notes.
         */
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.vurt.task.definition";
        
        /**
         * The MIME type of a {@link #CONTENT_URI} sub-directory of a single note.
         */
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.vurt.task.definition";

        /**
         * The name of the task definition
         * <P>Type: TEXT</P>
         */
        public static final String NAME = "name";

        /**
         * The description of the task
         * <P>Type: TEXT</P>
         */
        public static final String DESCRIPTION = "description";

        public static final String PAGES = "pages";
        
        /**
         * The default sort order for this table
         */
        public static final String DEFAULT_SORT_ORDER = NAME + " ASC";
        
        //TODO: Figure out how to store the page definitions.
        //Probably another table? Linked on definition id?
	}
	
	public final static class Instance implements BaseColumns {
		
	}
}
