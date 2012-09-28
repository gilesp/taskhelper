package uk.co.vurt.hakken.server.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import uk.co.vurt.hakken.domain.job.JobDefinition;
import uk.co.vurt.hakken.security.HashUtils;
import uk.co.vurt.hakken.server.exception.HakkenException;
import uk.co.vurt.hakken.server.service.JobService;

@Controller
@RequestMapping("/jobs")
public class JobController extends RESTController{

	@Autowired
	private JobService service;
	
	private static final Logger logger = LoggerFactory.getLogger(JobController.class);
	
	@RequestMapping(value="{id}", method=RequestMethod.GET)
	public @ResponseBody JobDefinition getJobById(@PathVariable long id){
		return service.get(id);
	}

	@RequestMapping("test")
	public @ResponseBody List<JobDefinition> getJobsList(){
		return service.getForUserSince("j.brookes", new Date());
	}
	
	@RequestMapping(value="for/{username}/since/{timestamp}", method=RequestMethod.GET)
	public @ResponseBody List<JobDefinition> getJobsForUserSince(@PathVariable String username, @RequestParam String hmac, @PathVariable String timestamp) throws HakkenException{
		Map<String, String>parameterMap = new HashMap<String, String>();
		parameterMap.put("username", username);
		parameterMap.put("timestamp", timestamp);
		
		boolean validRequest = HashUtils.validate(parameterMap, hmac);
		if(validRequest){
			SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			Date since = null;
			try {
				since = dateFormatter.parse(timestamp);
			} catch (ParseException e) {
				logger.error("Invalid timestamp provided", e);
				throw new HakkenException("Invalid timestamp provided.");
			}
			return service.getForUserSince(username, since);
		}else {
			logger.warn("Woop Woop! Invalid request received!");
			throw new HakkenException("Invalid request received");
		}
	}
}
