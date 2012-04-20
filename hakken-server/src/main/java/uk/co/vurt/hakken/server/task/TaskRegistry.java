package uk.co.vurt.hakken.server.task;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import uk.co.vurt.hakken.domain.task.TaskDefinition;

public class TaskRegistry {

	private static final Logger logger = LoggerFactory.getLogger(TaskRegistry.class);
	
	@Autowired
	TaskSourceService taskSource;
	
	//In memory storage for task definitions
	Map<String, TaskDefinition> tasks;
	
	private TaskRegistry(){
		tasks = new HashMap<String, TaskDefinition>();
		for(TaskDefinition definition: taskSource.getTaskDefinitions()){
			register(definition);
		}
	}
	
	private static class TaskRegistryHolder {
		public static final TaskRegistry instance = new TaskRegistry();
	}
	
	public static TaskRegistry getInstance(){
		return TaskRegistryHolder.instance;
	}
	
	public TaskDefinition getTask(String name){
		return tasks.get(name);
	}
	
	public void register(TaskDefinition task){
		logger.debug("Registering task " + task);
		tasks.put(task.getName(), task);
	}
}
