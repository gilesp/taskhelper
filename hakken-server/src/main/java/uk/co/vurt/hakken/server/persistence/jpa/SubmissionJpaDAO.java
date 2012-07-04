package uk.co.vurt.hakken.server.persistence.jpa;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import uk.co.vurt.hakken.domain.job.JobDefinition;
import uk.co.vurt.hakken.domain.job.Submission;
import uk.co.vurt.hakken.server.persistence.SubmissionDAO;

@Repository
public class SubmissionJpaDAO extends AbstractJpaDAO<Long, Submission>
		implements SubmissionDAO {

	@Override
	public Submission getByJobId(Long jobId) {
		Query query = entityManager.createQuery("from " + clazz.getName() + " where jobId = :jobId");
		query.setParameter("jobId", jobId);
		List<Submission> submissions = query.getResultList();
		if(submissions.size() > 0){
			return submissions.get(0);
		}else{
			return null;
		}
	}
}
