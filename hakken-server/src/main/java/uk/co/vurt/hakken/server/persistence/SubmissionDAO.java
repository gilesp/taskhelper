package uk.co.vurt.hakken.server.persistence;

import uk.co.vurt.hakken.domain.job.Submission;

public interface SubmissionDAO extends AbstractDAO<Long, Submission> {

	public Submission getByJobId(Long jobId);
}
