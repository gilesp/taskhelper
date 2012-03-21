package uk.co.vurt.taskhelper.server.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import uk.co.vurt.taskhelper.server.domain.definition.ManualTaskDefinition;

@RequestMapping("/manualtaskdefinitions")
@Controller
@RooWebScaffold(path = "manualtaskdefinitions", formBackingObject = ManualTaskDefinition.class)
public class ManualTaskDefinitionController {
	
	@RequestMapping(value = "/json/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Object jsonTaskDefinition(@PathVariable("id") Long id){
		ManualTaskDefinition definition = ManualTaskDefinition.findManualTaskDefinition(id);
		return definition.jsonify();
	}
}
