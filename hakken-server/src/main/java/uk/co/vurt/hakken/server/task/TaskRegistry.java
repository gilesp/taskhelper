package uk.co.vurt.hakken.server.task;

import uk.co.vurt.taskhelper.server.domain.definition.TaskDefinition;

public class TaskRegistry {

	private TaskRegistry(){}
	
	private static class TaskRegistryHolder {
		public static final TaskRegistry instance = new TaskRegistry();
	}
	
	public static TaskRegistry getInstance(){
		return TaskRegistryHolder.instance;
	}
	
	public TaskDefinition getTask(String name){
		return null;
	}
}
