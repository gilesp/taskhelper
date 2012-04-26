package uk.co.vurt.hakken.server.task;

import java.util.List;

import uk.co.vurt.hakken.domain.task.TaskDefinition;

public interface TaskSourceService {

	List<TaskDefinition> getTaskDefinitions();
	
	void reload();
}
