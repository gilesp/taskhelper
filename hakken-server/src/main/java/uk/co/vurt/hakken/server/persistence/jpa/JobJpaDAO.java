package uk.co.vurt.hakken.server.persistence.jpa;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import uk.co.vurt.hakken.server.persistence.JobDAO;
import uk.co.vurt.taskhelper.server.domain.job.Job;

@Repository
public class JobJpaDAO extends GenericJpaDAO<Long, Job> implements JobDAO {

	@Override
	public Job getByName(String name) {
		Query query = entityManager.createQuery("from " + clazz.getName() + " where name = :name");
		query.setParameter("name", name);
		List<Job> jobs = query.getResultList();
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
