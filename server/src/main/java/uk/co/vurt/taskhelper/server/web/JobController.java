package uk.co.vurt.taskhelper.server.web;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import uk.co.vurt.taskhelper.server.domain.definition.ManualTaskDefinition;
import uk.co.vurt.taskhelper.server.domain.job.DataItem;
import uk.co.vurt.taskhelper.server.domain.job.Job;
import uk.co.vurt.taskhelper.server.domain.job.Status;
import uk.co.vurt.taskhelper.server.domain.user.Person;
import uk.co.vurt.taskhelper.server.providers.HSCJobJDBCProvider;
import uk.co.vurt.taskhelper.server.providers.HSCJobProvider;

@RequestMapping("/jobs")
@Controller
@RooWebScaffold(path = "jobs", formBackingObject = Job.class)
@RooWebJson(jsonObject = Job.class)
public class JobController {

    @RequestMapping(value = "/for/{username}/{lastUpdated}", method = RequestMethod.POST)
    @ResponseBody
    public Object jsonJobsForPerson(@PathVariable("username") String username, @PathVariable("lastUpdated") String lastUpdated) {
    	System.out.println("USERNAME: " + username);
    	System.out.println("LASTUPDATED: " + lastUpdated);
    	
        Person person = Person.findPeopleByUsernameLike(username).getSingleResult();
        if (person == null) {
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }
        List<Job> newJobs = new ArrayList<Job>();
        for (Job job : Job.findJobsByPerson(person).getResultList()) {
            if (Status.NEW == job.getStatus()) {
                newJobs.add(job);
                System.out.println("Job has " + job.getDataItems().size() + " dataitems.");
                for(DataItem item: job.getDataItems()){
                	System.out.println("name: " + item.getName() + " value: " + item.getValue());
                }
            }
        }
        
        //retrieve taskgroup jobs
        TypedQuery<ManualTaskDefinition> definitions = ManualTaskDefinition.findManualTaskDefinitionsByNameLike("HSC Assessment");
        System.out.println("DEFINITIONS: " + definitions);
        if(definitions != null){
        	HSCJobProvider hscProvider = new HSCJobJDBCProvider(person, definitions.getSingleResult());
        	//these jobs needs saving to the database, in order to give them an ID.
        	List<Job> taskgroupJobs = hscProvider.getJobsForTaskGroup(person.getTaskgroup(), lastUpdated);
        	for(Job job: taskgroupJobs){
        		//if the job doesn't already exist in the user's list, then save it.
        		if(!newJobs.contains(job)){
        			job.persist();
        			for(DataItem item: job.getDataItems()){
        				System.out.println("Persisting dataitem: " + item.getName() + " with value " + item.getValue());
        				item.persist();
        			}
        			newJobs.add(job);
        		}
        	}
        	
//        	newJobs.addAll(taskgroupJobs);
        }
        return Job.jsonifyArray(newJobs);
    }
    
    @RequestMapping(value = "/json/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Object jsonTaskDefinition(@PathVariable("id") Long id){
    	return Job.findJob(id).jsonify();
    }
}
