package uk.co.vurt.hakken.server.web.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import uk.co.vurt.hakken.domain.job.Submission;
import uk.co.vurt.hakken.domain.job.SubmissionStatus;
import uk.co.vurt.hakken.server.service.SubmissionService;

@Controller
@RequestMapping(value="/admin/submission")
public class SubmissionAdminController {

	private static final Logger logger = LoggerFactory.getLogger(SubmissionAdminController.class);

	@Autowired
	SubmissionService submissionService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model){
		model.addAttribute("submissions", submissionService.getAll());
		return "submissions";
	}
	
	@RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
	public String delete(@PathVariable Long id,Model model) {
		if(id != null){
			submissionService.delete(id);
		}
		model.addAttribute("submissions", submissionService.getAll());
		return "redirect:/admin/submission/";
	}
	
	@RequestMapping(value = "/{id}/resubmit", method = RequestMethod.GET)
	public String resubmit(@PathVariable Long id,Model model) {
		Submission submission = submissionService.get(id);
		if(submission != null){
			SubmissionStatus status = submissionService.submit(submission);
			logger.debug("Resubmitted " + id + ": " + status.isValid());
		}
		return "redirect:/admin/submission/";
	}
}
