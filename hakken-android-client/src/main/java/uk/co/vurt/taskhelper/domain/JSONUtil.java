package uk.co.vurt.taskhelper.domain;

import uk.co.vurt.hakken.domain.job.JobDefinition;
import uk.co.vurt.hakken.domain.task.TaskDefinition;

import com.google.gson.Gson;

public class JSONUtil {

	Gson gson;
	
	private JSONUtil(){
		gson = new Gson();
	}
	
	private static class JSONUtilHolder {
		public static final JSONUtil INSTANCE = new JSONUtil();
	}
	
	public static JSONUtil getInstance(){
		return JSONUtilHolder.INSTANCE;
	}
	
	public String toJson(Object pojo){
		return gson.toJson(pojo);
	}
	
	public TaskDefinition parseTaskDefinition(String json){
		return gson.fromJson(json, TaskDefinition.class);
	}
	
	public JobDefinition parseJobDefinition(String json){
		return gson.fromJson(json, JobDefinition.class);
	}
	
}
