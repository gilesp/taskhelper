package uk.co.vurt.hakken.server.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import uk.co.vurt.hakken.server.persistence.GenericDAO;
import uk.co.vurt.hakken.server.persistence.JobDAO;
import uk.co.vurt.taskhelper.server.domain.job.Job;

@Service
public class JobServiceImpl implements JobService{

	JobDAO dao;
	

	public Job getByName(String name){
		return dao.getByName(name);
	}

	@Override
	public void setDao(GenericDAO<Long, Job> dao) {
		this.dao = (JobDAO) dao;
		this.dao.setClazz(Job.class);
	}

	@Override
	public List<Job> getForUserSince(String username, String timestamp) {
//		return dao.getByUserAndDate(username, timestamp);
		//TODO: Implement a mechanism for mapping task definitions to sources of job instances
		
		//find out which task definitions the user has access to
		
		//for each task definition, lookup instance provider/data connector and invoke.
		
		return new ArrayList<Job>();
	}
}
