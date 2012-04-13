package uk.co.vurt.hakken.server;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import uk.co.vurt.hakken.server.model.Job;

@Controller
@RequestMapping("/jobs")
public class JobController {

	@RequestMapping(value="{id}", method=RequestMethod.GET)
	public @ResponseBody Job getJobById(@PathVariable int id){
		//TODO: implement job functionality
		Job job = new Job();
		
		return job;
	}
	
	@RequestMapping(value="{name}", method=RequestMethod.GET)
	public @ResponseBody Job getJobByName(@PathVariable String name){
		Job job = new Job();
		return job;
	}
	
	@RequestMapping(value="for/{username}", method=RequestMethod.GET)
	public @ResponseBody List<Job> getJobsForUser(@PathVariable String username){
		List<Job> jobs = new ArrayList<Job>();
		return jobs;
	}
	
	@RequestMapping(value="for/{username}/since/{timestamp}", method=RequestMethod.GET)
	public @ResponseBody List<Job> getJobsForUserSince(@PathVariable String username, @PathVariable String timestamp){
		List<Job> jobs = new ArrayList<Job>();
		return jobs;
	}
}
