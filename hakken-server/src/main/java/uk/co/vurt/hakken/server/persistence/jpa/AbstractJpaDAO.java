package uk.co.vurt.hakken.server.persistence.jpa;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import uk.co.vurt.hakken.server.persistence.AbstractDAO;

public abstract class AbstractJpaDAO<ID, T extends Serializable> implements AbstractDAO<ID, T> {

	private static final Logger logger = LoggerFactory.getLogger(AbstractJpaDAO.class);
	
	protected Class<T> clazz;
	
	EntityManager entityManager;
	
	public void setClazz(final Class<T> clazz){
		logger.info("Setting clazz: " + clazz);
		this.clazz = clazz;
	}
	
	@Transactional(readOnly = true)
	public T get(final ID id){
		logger.info("Looking up " + clazz.getName() + " with id: " + id);
		return entityManager.find(clazz, id);
	}
	
	@SuppressWarnings(value = "unchecked")
	@Transactional(readOnly = true)
	public List<T> getAll(){
		return entityManager.createQuery("from " + clazz.getName()).getResultList();
	}

	@Transactional
	public void save(final T entity){
		logger.info("About to save: " + entity);
		try{
			entityManager.persist(entity);
			entityManager.persist(entity);
		}catch(Throwable t){
			logger.error("Unable to save: ", t);
		}
		logger.info("Saved.");
	}
	
	@Transactional
	public void update(final T entity){
		entityManager.merge(entity);
	}
	
	@Transactional
	public void delete(final T entity){
		entityManager.remove(entity);
	}
	
	@Transactional
	public void deleteById(final ID entityId){
		final T entity = get(entityId);
		delete(entity);
	}

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		logger.info("Setting entityManager to: " + entityManager);
		this.entityManager = entityManager;
	}
}
