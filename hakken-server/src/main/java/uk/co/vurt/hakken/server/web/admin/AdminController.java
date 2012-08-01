package uk.co.vurt.hakken.server.web.admin;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import uk.co.vurt.hakken.domain.task.TaskDefinition;
import uk.co.vurt.hakken.server.connector.ConfigProperty;
import uk.co.vurt.hakken.server.connector.DataConnector;
import uk.co.vurt.hakken.server.connector.DataConnectorTaskDefinition;
import uk.co.vurt.hakken.server.mapping.DataConnectorTaskDefinitionMapping;
import uk.co.vurt.hakken.server.mapping.ServiceMapping;
import uk.co.vurt.hakken.server.service.DataConnectorService;
import uk.co.vurt.hakken.server.service.DataConnectorTaskDefinitionMappingService;
import uk.co.vurt.hakken.server.service.LogService;
import uk.co.vurt.hakken.server.service.MappingService;
import uk.co.vurt.hakken.server.task.TaskRegistry;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	
	@Autowired
	TaskRegistry taskRegistry;
	@Autowired
	DataConnectorService dataConnectorService;
	@Autowired
	DataConnectorTaskDefinitionMappingService definitionMappingService;
	@Autowired
	MappingService mappingService;
	@Autowired
	LogService logService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model){
		model.addAttribute("taskDefinitions", taskRegistry.getAllTasks());
		model.addAttribute("dcDefinitionMappings", definitionMappingService.getAll());
		model.addAttribute("dataConnectors", dataConnectorService.getDataConnectorsMap());
		model.addAttribute("mappings", mappingService.getAll());
		return "admin";
	}
	
	@RequestMapping(value = "/task/{name}", method = RequestMethod.GET)
	public String viewTask(@PathVariable String name, Model model) throws JsonGenerationException, JsonMappingException, IOException{
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationConfig.Feature.INDENT_OUTPUT, true);
		model.addAttribute("task", taskRegistry.getTask(name));
		model.addAttribute("taskAsJson", mapper.writeValueAsString(taskRegistry.getTask(name)));
		model.addAttribute("dcDefinitionMappings", definitionMappingService.getAll());
		return "task";
	}
	
	@RequestMapping(value = "/dataconnector/{name}", method = RequestMethod.GET)
	public String viewDataConnector(@PathVariable String name, Model model){
		model.addAttribute("dataConnector", dataConnectorService.getDataConnector(name));
		model.addAttribute("connectorName", name);
		return "dataconnector";
	}
	
	@RequestMapping(value = "/dataconnector/newmapping", method = RequestMethod.POST)
	public String viewCreateDataConnectorTaskDefinitionMapping(@RequestParam String connectorName, @RequestParam String definitionName, Model model){
		DataConnector connector = dataConnectorService.getDataConnector(connectorName);
		model.addAttribute("dataConnector", connector);
		model.addAttribute("definition", connector.getDefinition(definitionName));
		
		return "createdefinitionmapping";
	}
	
	@RequestMapping(value = "/dataconnector/savemapping", method = RequestMethod.POST)
	public String createDataConnectorTaskDefinitionMapping(HttpServletRequest request){
		String definitionName = request.getParameter("definitionName");
		String connectorName = request.getParameter("connectorName");
		DataConnector connector = dataConnectorService.getDataConnector(connectorName);
		DataConnectorTaskDefinitionMapping mapping = new DataConnectorTaskDefinitionMapping();
		mapping.setDataConnectorName(connectorName);
		mapping.setTaskDefinitionName(definitionName);
		DataConnectorTaskDefinition definition = connector.getDefinition(definitionName);
		if(definition != null){
			if(definition.getConfigProperties() != null){
				for(ConfigProperty configProperty: definition.getConfigProperties()){
//					configProperty.setValue(request.getParameter(configProperty.getName()));
					mapping.setProperty(configProperty.getName(), request.getParameter(configProperty.getName()));
				}
//				for(String propertyName: definition.getPropertyNames()){
//					mapping.setProperty(propertyName, request.getParameter(propertyName));
//				}
			}
			definitionMappingService.save(mapping);
		}
		
		return "redirect:/admin/";
	}
	
	@RequestMapping(value="/dataconnector/{connectorName}/{definitionName}", method=RequestMethod.GET)
	public String viewDataConnectorTaskDefinition(@PathVariable String connectorName, @PathVariable String definitionName, Model model){
		model.addAttribute("definition", dataConnectorService.getDataConnector(connectorName).getDefinition(definitionName));
		model.addAttribute("connectorName", connectorName);
//		model.addAttribute("taskDefinitions", taskRegistry.getAllTasks());
		return "dataconnectordefinition";
	}
	
	@RequestMapping(value = "/mapping/new", method = RequestMethod.POST)
	public String createMapping(@RequestParam Long dcTaskDefMappingId, @RequestParam String taskName, Model model){
		DataConnectorTaskDefinitionMapping dcTaskDefinitionMapping = definitionMappingService.get(dcTaskDefMappingId);
		DataConnector dataConnector = dataConnectorService.getDataConnector(dcTaskDefinitionMapping.getDataConnectorName());

		model.addAttribute("dcTaskDefMappingId", dcTaskDefMappingId);
		model.addAttribute("dcTaskDefMappingName", dcTaskDefinitionMapping.toString());
		model.addAttribute("dcTaskDefinition", dataConnector.getDefinition(dcTaskDefinitionMapping.getTaskDefinitionName()));
		model.addAttribute("taskDefinition", taskRegistry.getTask(taskName));
		return "createmapping";
	}
	
	@RequestMapping(value = "/mapping/save", method = RequestMethod.POST)
	public String createMapping(HttpServletRequest request/*, @RequestParam String taskName, @RequestParam String connectorName*/){
		String taskName = request.getParameter("taskName");
		Long dataConnectorTaskDefinitionId = Long.valueOf(request.getParameter("dcTaskDefMappingId"));
		
		
//		logger.info("TaskName: " + taskName + " connectorName: " + connectorName);
		
		TaskDefinition task = taskRegistry.getTask(taskName);
		DataConnectorTaskDefinitionMapping dcTaskDefinitionMapping = definitionMappingService.get(dataConnectorTaskDefinitionId);
		
//		DataConnector connector = dataConnectorService.getDataConnector(connectorName);
//		logger.info("TASK : " + task);
//		logger.info("CONNECTOR: " + connector);
		
		if(task != null && dcTaskDefinitionMapping != null){
			ServiceMapping mapping = new ServiceMapping();
			mapping.setTaskDefinitionName(taskName);
			mapping.setDataConnectorTaskDefinitionMapping(dcTaskDefinitionMapping);

			Enumeration parameterNames = request.getParameterNames();
			while(parameterNames.hasMoreElements()){
				String paramName = (String)parameterNames.nextElement();
				if(paramName.contains("@@")){
					logger.info("Found page item parameter: " + paramName);
					mapping.setMapping(request.getParameter(paramName), paramName);
				}
			}
			logger.info("MAPPING: " + mapping.toString());
			mappingService.save(mapping);
		}
		return("redirect:/admin/");
	}
	
	@RequestMapping(value="/mapping/{id}", method=RequestMethod.GET)
	public String viewMapping(@PathVariable long id, Model model){
		model.addAttribute("mapping", mappingService.get(id));
		return ("mapping");
	}
	
	@RequestMapping("/reloadTasks")
	public String reloadTasks(Model model){
		taskRegistry.reload();
		return home(model);
	}
	
	@RequestMapping("/logs")
	public String viewLogs(Model model){
		model.addAttribute("logs", logService.getAll());
		return("viewlogs");
	}
}
