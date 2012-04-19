package uk.co.vurt.hakken.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import uk.co.vurt.hakken.security.HashUtils;
import uk.co.vurt.hakken.server.service.JobService;
import uk.co.vurt.taskhelper.server.domain.job.Job;

@Controller
@RequestMapping("/jobs")
public class JobController {

	@Autowired
	private JobService service;
	
	private static final Logger logger = LoggerFactory.getLogger(JobController.class);
	
	@RequestMapping(value="{id}", method=RequestMethod.GET)
	public @ResponseBody Job getJobById(@PathVariable int id){
		//TODO: implement job functionality
		Job job = new Job();
		
		return job;
	}
	
//	@RequestMapping(value="{name}", method=RequestMethod.GET)
//	public @ResponseBody Job getJobByName(@PathVariable String name){
//		Job job = new Job();
//		return job;
//	}
	
//	@RequestMapping(value="for/{username}", method=RequestMethod.GET)
//	public @ResponseBody List<Job> getJobsForUser(@PathVariable String username){
//		List<Job> jobs = new ArrayList<Job>();
//		return jobs;
//	}
	
	@RequestMapping(value="for/{username}/{hmac}/since/{timestamp}", method=RequestMethod.GET)
	public @ResponseBody List<Job> getJobsForUserSince(@PathVariable String username, @PathVariable String hmac, @PathVariable String timestamp){
		Map<String, String>parameterMap = new HashMap<String, String>();
		parameterMap.put("username", username);
		parameterMap.put("timestamp", timestamp);
		
		boolean validRequest = HashUtils.validate(parameterMap, hmac);
		if(validRequest){
//			List<Job> jobs = new ArrayList<Job>();
//			//TODO: Actually retrieve job list
//			logger.debug("Valid request received.");
//			
//			Job testJob = new Job();
//			testJob.setId(1);
//			testJob.setCreated(new Date());
//			testJob.setName("Test");
//			testJob.setNotes("This is a test Job");
//			testJob.setDue(new Date());
//			
//			jobs.add(testJob);
//			
//			return jobs;
			
			return service.getForUserSince(username, timestamp);
		}else {
			//TODO: handle invalid request/send error response
			logger.warn("Woop Woop! Invalid request received!");
			return null;
		}
	}
}
