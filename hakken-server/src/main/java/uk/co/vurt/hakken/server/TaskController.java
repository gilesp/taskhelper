package uk.co.vurt.hakken.server;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import uk.co.vurt.hakken.domain.task.TaskDefinition;
import uk.co.vurt.hakken.server.task.TaskRegistry;


@Controller
@RequestMapping("/tasks")
public class TaskController {

	@RequestMapping(value="{id}", method=RequestMethod.GET)
	public @ResponseBody TaskDefinition getTaskById(@PathVariable long id){
		return TaskRegistry.getInstance().getTask(id);
	}
	
	@RequestMapping(value="{name}", method=RequestMethod.GET)
	public @ResponseBody TaskDefinition getTaskByName(@PathVariable String name){
		return TaskRegistry.getInstance().getTask(name);
	}
}
