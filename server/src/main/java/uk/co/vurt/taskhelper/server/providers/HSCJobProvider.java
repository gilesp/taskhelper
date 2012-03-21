package uk.co.vurt.taskhelper.server.providers;

import java.util.Date;
import java.util.List;

import uk.co.vurt.taskhelper.server.domain.job.Job;

public interface HSCJobProvider {

	public List<Job> getJobsForTaskGroup(String taskGroup, String lastUpdated);
	
}
