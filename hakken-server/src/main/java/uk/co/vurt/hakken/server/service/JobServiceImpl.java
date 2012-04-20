package uk.co.vurt.hakken.server.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import uk.co.vurt.hakken.domain.job.JobDefinition;
import uk.co.vurt.hakken.server.persistence.GenericDAO;
import uk.co.vurt.hakken.server.persistence.JobDAO;

@Service
public class JobServiceImpl implements JobService{

	JobDAO dao;
	

	public JobDefinition getByName(String name){
		return dao.getByName(name);
	}

	@Override
	public void setDao(GenericDAO<Long, JobDefinition> dao) {
		this.dao = (JobDAO) dao;
		this.dao.setClazz(JobDefinition.class);
	}

	@Override
	public List<JobDefinition> getForUserSince(String username, String timestamp) {
//		return dao.getByUserAndDate(username, timestamp);
		//TODO: Implement a mechanism for mapping task definitions to sources of job instances
		
		//find out which task definitions the user has access to
		
		//for each task definition, lookup instance provider/data connector and invoke.
		
		return new ArrayList<JobDefinition>();
	}

	@Override
	public JobDefinition get(Long id) {
		return dao.get(id);
	}
}
