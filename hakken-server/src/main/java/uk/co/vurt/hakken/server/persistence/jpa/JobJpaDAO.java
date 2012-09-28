package uk.co.vurt.hakken.server.persistence.jpa;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import uk.co.vurt.hakken.domain.job.JobDefinition;
import uk.co.vurt.hakken.server.persistence.JobDAO;

@Repository
public class JobJpaDAO extends AbstractJpaDAO<Long, JobDefinition> implements JobDAO {

	@Override
	@Transactional(readOnly = true)
	public JobDefinition getByName(String name) {
		Query query = entityManager.createQuery("from " + clazz.getName() + " where name = :name");
		query.setParameter("name", name);
		List<JobDefinition> jobs = query.getResultList();
		if(jobs.size() > 0){
			return jobs.get(0);
		}else{
			return null;
		}
	}
//
//	@Override
//	public List<Job> getByUsernameAndDate(String username, String timestamp) {
//		Query query = entityManager.createQuery("from " + clazz.getName() + " where user")
//		return null;
//	}

}
