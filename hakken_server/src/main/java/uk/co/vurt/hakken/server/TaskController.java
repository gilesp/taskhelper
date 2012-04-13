package uk.co.vurt.hakken.server;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import uk.co.vurt.hakken.server.model.Task;

@Controller
@RequestMapping("/tasks")
public class TaskController {

	@RequestMapping(value="{id}", method=RequestMethod.GET)
	public @ResponseBody Task getTaskById(@PathVariable int id){
		//TODO: implement job functionality
		Task task = new Task();
		
		return task;
	}
	
	@RequestMapping(value="{name}", method=RequestMethod.GET)
	public @ResponseBody Task getTaskByName(@PathVariable String name){
		Task task = new Task();
		return task;
	}
}
