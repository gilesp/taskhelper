package uk.co.vurt.taskhelper.server.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.co.vurt.taskhelper.server.domain.job.DataItem;

@RequestMapping("/dataitems")
@Controller
@RooWebScaffold(path = "dataitems", formBackingObject = DataItem.class)
public class DataItemController {
}
