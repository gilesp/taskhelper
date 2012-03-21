package uk.co.vurt.taskhelper.server.web;

import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.co.vurt.taskhelper.server.domain.definition.TaskPage;

@RequestMapping("/taskpages")
@Controller
@RooWebScaffold(path = "taskpages", formBackingObject = TaskPage.class)
@RooWebJson(jsonObject = TaskPage.class)
public class TaskPageController {
}
