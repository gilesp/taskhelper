package uk.co.vurt.taskhelper.processor;

import java.util.List;

import uk.co.vurt.hakken.domain.task.Page;
import uk.co.vurt.hakken.domain.task.TaskDefinition;
import uk.co.vurt.taskhelper.providers.Task;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.google.gson.Gson;

public class TaskProcessor {

	private static final String TAG = "TaskProcessor";
	
	private static final String[] PROJECTION = new String[] {
		Task.Definitions._ID,
		Task.Definitions.JSON
	};
	
	private static final int COLUMN_INDEX_TASKDEFINITION_ID = 0;
	private static final int COLUMN_INDEX_TASKDEFINITION_JSON = 1;
	
	private TaskDefinition taskDefinition;
	
	
	public TaskProcessor(ContentResolver contentResolver, Uri taskUri){
		Cursor definitionCursor = contentResolver.query(taskUri, TaskProcessor.PROJECTION, null, null, null);
		if(definitionCursor != null){
			definitionCursor.moveToFirst();
			init(definitionCursor.getString(COLUMN_INDEX_TASKDEFINITION_JSON));
			definitionCursor.close();
		}
	}
	
	public void init(String taskDefinitionJson){
		setTaskDefinition(new Gson().fromJson(taskDefinitionJson, TaskDefinition.class));
	}

	public TaskDefinition getTaskDefinition() {
		return taskDefinition;
	}

	public void setTaskDefinition(TaskDefinition taskDefinition) {
		this.taskDefinition = taskDefinition;
	}
	
	public String getTaskName(){
		if(taskDefinition != null){
			return taskDefinition.getName();
		}else {
			return null;
		}
	}
	
	public List<Page> getPages(){
		return taskDefinition.getPages();
	}
}
