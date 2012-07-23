package uk.co.vurt.hakken.server.service;

import java.util.List;

import uk.co.vurt.hakken.domain.job.Submission;

public interface SubmissionService extends Service<Long, Submission> {

	void store(Submission submission);

	List<Submission> getAll();
	
	void delete(long id);
	
	boolean submit(Submission submission);
}
