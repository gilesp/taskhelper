package uk.co.vurt.hakken.server.service;

import uk.co.vurt.hakken.domain.job.Submission;

public interface SubmissionService extends Service<Long, Submission> {

	void store(Submission submission);

}
