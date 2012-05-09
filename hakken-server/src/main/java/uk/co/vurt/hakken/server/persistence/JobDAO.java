package uk.co.vurt.hakken.server.persistence;

import uk.co.vurt.hakken.domain.job.JobDefinition;

public interface JobDAO extends AbstractDAO<Long, JobDefinition> {

	public JobDefinition getByName(String name);
	
//	public List<Job> getByUsernameAndDate(String username, String timestamp);
}
