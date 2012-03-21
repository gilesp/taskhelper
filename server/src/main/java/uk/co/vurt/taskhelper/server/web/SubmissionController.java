package uk.co.vurt.taskhelper.server.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uk.co.vurt.taskhelper.server.domain.job.DataItem;
import uk.co.vurt.taskhelper.server.domain.job.Job;
import uk.co.vurt.taskhelper.server.domain.job.Status;
import uk.co.vurt.taskhelper.server.domain.job.Submission;

@RequestMapping("/submissions")
@Controller
@RooWebScaffold(path = "submissions", formBackingObject = Submission.class)
@RooWebJson(jsonObject = Submission.class)
public class SubmissionController {

    @RequestMapping(value = "/job", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<java.lang.String> saveSubmissions(@RequestBody String json) {
        HttpStatus status = HttpStatus.METHOD_FAILURE;
        System.out.println("SUBMISSION!");
        System.out.println("JSON: " + json);
        Submission submission = Submission.fromJsonToSubmission(json);
        if (submission != null) {
            System.out.println("USERNAME: " + submission.getUsername());
            boolean persisted = true;
            try {
                for (DataItem dataitem : submission.getDataitems()) {
                    try {
                        dataitem.persist();
                    } catch (Throwable t) {
                        System.out.println("Error persisting dataitem: " + t.getMessage());
                        persisted = false;
                    }
                }
                submission.persist();
            } catch (Throwable t) {
                System.out.println("ARGH!: " + t.getMessage());
                persisted = false;
            }
            System.out.println("Persisted. Returning with status: " + HttpStatus.CREATED.value());
            if (persisted) {
                Job job = Job.findJob((long) submission.getJobId());
                job.setStatus(Status.COMPLETED);
                try {
                    job.persist();
                    status = HttpStatus.CREATED;
                } catch (Throwable t) {
                    System.out.println("UNABLE TO PERSIST JOB: " + t.getMessage());
                    status = HttpStatus.ACCEPTED;
                }
            } else {
                status = HttpStatus.BAD_REQUEST;
            }
        } else {
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<String>(status);
    }
}
