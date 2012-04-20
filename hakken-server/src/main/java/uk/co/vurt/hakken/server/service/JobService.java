package uk.co.vurt.hakken.server.service;

import java.util.List;

import uk.co.vurt.hakken.domain.job.JobDefinition;

public interface JobService extends Service<Long, JobDefinition> {

	public JobDefinition getByName(String name);
	
	public List<JobDefinition> getForUserSince(String username, String timestamp);
	
}
