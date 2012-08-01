package uk.co.vurt.hakken.server.task;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import uk.co.vurt.hakken.domain.task.TaskDefinition;

public class TaskFileSourceService implements TaskSourceService, InitializingBean{

	private static final Logger logger = LoggerFactory.getLogger(TaskFileSourceService.class);
			
	private String taskDirectoryPaths;
	
	private ObjectMapper mapper;
	
	private List<TaskDefinition> taskDefinitions;

	public TaskFileSourceService(){
		mapper = new ObjectMapper();
		
		//had to remove the below properties as not valid in this form 
		//in Jackson 1.9.6
		
		// to prevent exception when encountering unknown property:
//		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		// to allow coercion of JSON empty String ("") to null Object value:
//		mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
	}
	
	public TaskDefinition load(File file){
		TaskDefinition task = null;
		
		try {
			task = mapper.readValue(file, TaskDefinition.class);
		} catch (JsonParseException e) {
			logger.error("Unable to parse task definition in file " + file.getName(), e);
		} catch (JsonMappingException e) {
			logger.error("Unable to map json to task definition.", e);
		} catch (IOException e) {
			logger.error("Unable to read file " + file.getName(), e);
		}
		
		return task;
	}

	public void reload(){
		logger.info("Reloading task definitions...");
		
		taskDefinitions = new ArrayList<TaskDefinition>();
		
		String[] paths = taskDirectoryPaths.split(",");
		
		for(String path: paths){
			logger.info("Attempting to load task definitions from " + path);
			File pathDir = new File(path);
			
			if(pathDir.exists()){
				File[] taskFiles = pathDir.listFiles(jsonFilter);
				for(File taskFile: taskFiles){
					TaskDefinition task = load(taskFile); 
//					logger.info("Adding task " + task);
					if(task != null){
						taskDefinitions.add(task);
					}
				}
			}
		}
		
		
		logger.info("Loaded " + taskDefinitions.size() + " definitions");
	}
	
	@Override
	public List<TaskDefinition> getTaskDefinitions() {
		return taskDefinitions;
	}

	public void setTaskDirectoryPaths(String taskDirectoryPaths) {
		this.taskDirectoryPaths = taskDirectoryPaths;
	}

	/**
	 * Called by spring after the bean has been configured
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		reload();
	}
	
//	private static FileFilter directoryFilter = new FileFilter() {
//		public boolean accept(File pathname) {
//			return pathname.isDirectory()
//					&& !pathname.getName().startsWith(".");
//		}
//	};
			
	private static FileFilter jsonFilter = new FileFilter() {
		public boolean accept(File pathname) {
			return pathname.isFile() && pathname.getName().endsWith(".json")
					&& !pathname.getName().startsWith(".");
		}
	};
}
