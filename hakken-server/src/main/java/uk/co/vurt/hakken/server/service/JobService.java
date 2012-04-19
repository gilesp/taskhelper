package uk.co.vurt.hakken.server.service;

import java.util.List;

import uk.co.vurt.taskhelper.server.domain.job.Job;

public interface JobService extends Service<Long, Job> {

	public Job getByName(String name);
	
	public List<Job> getForUserSince(String username, String timestamp);
	
}
