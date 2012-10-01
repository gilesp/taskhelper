package uk.co.vurt.hakken.domain;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import uk.co.vurt.hakken.domain.job.JobDefinition;
import uk.co.vurt.hakken.domain.job.SubmissionStatus;
import uk.co.vurt.hakken.domain.task.TaskDefinition;
import android.util.Log;

public class JSONUtil {

	private static final String TAG = "JSONUtil";
	
	private ObjectMapper mapper;
	
	private JSONUtil(){
		mapper = new ObjectMapper();
	}
	
	private static class JSONUtilHolder {
		public static final JSONUtil INSTANCE = new JSONUtil();
	}
	
	public static JSONUtil getInstance(){
		return JSONUtilHolder.INSTANCE;
	}
	
	public String toJson(Object pojo){
		String json = null;
		
		try {
			json = mapper.writeValueAsString(pojo);
		} catch (JsonGenerationException e) {
			Log.e(TAG, e.getMessage(), e);
		} catch (JsonMappingException e) {
			Log.e(TAG, e.getMessage(), e);
		} catch (IOException e) {
			Log.e(TAG, e.getMessage(), e);
		}
		return json;
	}
	
	public TaskDefinition parseTaskDefinition(String json){
		TaskDefinition task = null;
		try {
			task = mapper.readValue(json, TaskDefinition.class);
		} catch (JsonParseException e) {
			Log.e(TAG, e.getMessage(), e);
		} catch (JsonMappingException e) {
			Log.e(TAG, e.getMessage(), e);
		} catch (IOException e) {
			Log.e(TAG, e.getMessage(), e);
		}
		return task;
	}
	
	public JobDefinition parseJobDefinition(String json){
		JobDefinition job = null;
		try {
			job = mapper.readValue(json, JobDefinition.class);
		} catch (JsonParseException e) {
			Log.e(TAG, e.getMessage(), e);
		} catch (JsonMappingException e) {
			Log.e(TAG, e.getMessage(), e);
		} catch (IOException e) {
			Log.e(TAG, e.getMessage(), e);
		}
		
		return job;
	}
	
	public SubmissionStatus parseSubmissionStatus(String json) {
		
		SubmissionStatus status = null;
		try {
			status = mapper.readValue(json, SubmissionStatus.class);
		} catch (JsonParseException e) {
			Log.e(TAG, e.getMessage(), e);
		} catch (JsonMappingException e) {
			Log.e(TAG, e.getMessage(), e);
		} catch (IOException e) {
			Log.e(TAG, e.getMessage(), e);
		}
		return status;
	}
}
