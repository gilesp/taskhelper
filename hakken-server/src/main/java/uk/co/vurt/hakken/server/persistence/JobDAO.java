package uk.co.vurt.hakken.server.persistence;

import java.util.List;

import uk.co.vurt.taskhelper.server.domain.job.Job;

public interface JobDAO extends GenericDAO<Long, Job> {

	public Job getByName(String name);
	
//	public List<Job> getByUsernameAndDate(String username, String timestamp);
}
