package uk.co.vurt.taskhelper.server;

import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.co.vurt.taskhelper.server.domain.job.Status;

@RooWebJson(jsonObject = Status.class)
@Controller
@RequestMapping("/statuses")
public class StatusController {
}
