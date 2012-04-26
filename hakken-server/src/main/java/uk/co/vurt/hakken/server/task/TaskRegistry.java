package uk.co.vurt.hakken.server.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import uk.co.vurt.hakken.domain.task.TaskDefinition;

@Component
@Scope(value = "singleton")
public class TaskRegistry {

	private static final Logger logger = LoggerFactory.getLogger(TaskRegistry.class);
	
	@Autowired
	TaskSourceService taskSource;
	
	//In memory storage for task definitions
	Map<Long, TaskDefinition> tasks = null;
	Map<String, Long> nameMap = null;
	
	public TaskRegistry(){
		
	}
	
	private void init(){
		if(tasks == null && nameMap == null){
			tasks = new HashMap<Long, TaskDefinition>();
			nameMap = new HashMap<String, Long>();
			
			for(TaskDefinition definition: taskSource.getTaskDefinitions()){
				register(definition);
			}
		}
	}
	
//	private static class TaskRegistryHolder {
//		public static final TaskRegistry instance = new TaskRegistry();
//	}
//	
//	public static TaskRegistry getInstance(){
//		return TaskRegistryHolder.instance;
//	}
	
	public List<TaskDefinition> getAllTasks(){
		init();
		return new ArrayList<TaskDefinition>(tasks.values());
	}
	
	public TaskDefinition getTask(String name){
		init();
		return getTask(nameMap.get(name));
	}
	
	public TaskDefinition getTask(Long id){
		init();
		return tasks.get(id);
	}
	
	public void register(TaskDefinition task){
		logger.debug("Registering task " + task);
		tasks.put(task.getId(), task);
		nameMap.put(task.getName(), task.getId());
	}
	
	public void reload(){
		taskSource.reload();
		tasks = null;
		nameMap = null;
		init();
	}
}
