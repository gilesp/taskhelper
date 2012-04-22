package uk.co.vurt.taskhelper.domain;

import java.lang.reflect.Type;
import java.util.Date;

import uk.co.vurt.hakken.domain.job.JobDefinition;
import uk.co.vurt.hakken.domain.task.TaskDefinition;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class JSONUtil {

	Gson gson;
	
	private JSONUtil(){
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {

			public Date deserialize(JsonElement json, Type typeOfT,
					JsonDeserializationContext context)
					throws JsonParseException {
				return new Date(json.getAsJsonPrimitive().getAsLong()); 
			}
		});
		gson = builder.create();
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
