package uk.co.vurt.hakken.server.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import uk.co.vurt.hakken.server.service.DataConnectorService;
import uk.co.vurt.hakken.server.task.TaskRegistry;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

	@Autowired
	TaskRegistry taskRegistry;
	@Autowired
	DataConnectorService dataConnectorService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model){
		
		model.addAttribute("taskDefinitions", taskRegistry.getAllTasks());
//		model.addAttribute("dataConnectors", dataConnectorService.getConnectorNames());
		model.addAttribute("dataConnectors", dataConnectorService.getDataConnectorsMap());
		return "admin";
	}
	
	@RequestMapping(value = "/task/{name}", method = RequestMethod.GET)
	public String viewTask(@PathVariable String name, Model model){
		model.addAttribute("task", taskRegistry.getTask(name));
		return "task";
	}
	
	@RequestMapping(value = "/dataconnector/{name}", method = RequestMethod.GET)
	public String viewDataConnector(@PathVariable String name, Model model){
		model.addAttribute("dataConnector", dataConnectorService.getDataConnector(name));
		return "dataconnector";
	}
	
	@RequestMapping("/reloadTasks")
	public String reloadTasks(Model model){
		taskRegistry.reload();
		return home(model);
	}
}
