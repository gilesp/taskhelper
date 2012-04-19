package uk.co.vurt.hakken.server.persistence.jpa;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import uk.co.vurt.hakken.server.persistence.AbstractDAO;

public abstract class AbstractJpaDAO<ID, T extends Serializable> implements AbstractDAO<ID, T> {

	protected Class<T> clazz;
	
	@PersistenceContext
	EntityManager entityManager;
	
	public void setClazz(final Class<T> clazz){
		this.clazz = clazz;
	}
	
	@Transactional(readOnly = true)
	public T get(final ID id){
		return entityManager.find(clazz, id);
	}
	
	@Transactional(readOnly = true)
	public List<T> getAll(){
		return entityManager.createQuery("from " + clazz.getName()).getResultList();
	}
	
	public void save(final T entity){
		entityManager.persist(entity);
	}
	
	public void update(final T entity){
		entityManager.merge(entity);
	}
	
	public void delete(final T entity){
		entityManager.remove(entity);
	}
	
	public void deleteById(final ID entityId){
		final T entity = get(entityId);
		delete(entity);
	}
}
