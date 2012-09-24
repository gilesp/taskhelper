package uk.co.vurt.hakken.domain;

import java.io.IOException;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import uk.co.vurt.hakken.domain.job.JobDefinition;
import uk.co.vurt.hakken.domain.job.SubmissionStatus;
import uk.co.vurt.hakken.domain.task.TaskDefinition;

public class JSONUtil {

	private ObjectMapper mapper;
//	Gson gson;
	
	private JSONUtil(){
		mapper = new ObjectMapper();
		
//		GsonBuilder builder = new GsonBuilder();
//		builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
//
//			public Date deserialize(JsonElement json, Type typeOfT,
//					JsonDeserializationContext context)
//					throws JsonParseException {
//				return new Date(json.getAsJsonPrimitive().getAsLong()); 
//			}
//		});
//		gson = builder.create();
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
//		return gson.toJson(pojo);
	}
	
	public TaskDefinition parseTaskDefinition(String json){
		TaskDefinition task = null;
		try {
			task = mapper.readValue(json, TaskDefinition.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return task;
	}
	
	public JobDefinition parseJobDefinition(String json){
		JobDefinition job = null;
		try {
			job = mapper.readValue(json, JobDefinition.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return job;
//		return gson.fromJson(json, JobDefinition.class);
	}
	
	public SubmissionStatus parseSubmissionStatus(String json) {
		
		SubmissionStatus status = null;
		try {
			status = mapper.readValue(json, SubmissionStatus.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}
}
