package uk.co.vurt.taskhelper.server;

import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.co.vurt.taskhelper.server.domain.definition.InputTypes;

@RooWebJson(jsonObject = InputTypes.class)
@Controller
@RequestMapping("/inputtypeses")
public class InputTypesController {
}
