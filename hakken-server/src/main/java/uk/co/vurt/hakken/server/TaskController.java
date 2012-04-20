package uk.co.vurt.hakken.server;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import uk.co.vurt.hakken.domain.task.StaticTaskDefinition;


@Controller
@RequestMapping("/tasks")
public class TaskController {

	@RequestMapping(value="{id}", method=RequestMethod.GET)
	public @ResponseBody StaticTaskDefinition getTaskById(@PathVariable int id){
		StaticTaskDefinition definition = new StaticTaskDefinition();
		return definition;
	}
	
	@RequestMapping(value="{name}", method=RequestMethod.GET)
	public @ResponseBody StaticTaskDefinition getTaskByName(@PathVariable String name){
		StaticTaskDefinition definition = new StaticTaskDefinition();
		return definition;
	}
}
