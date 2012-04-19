package uk.co.vurt.hakken.server.persistence;

import java.io.Serializable;
import java.util.List;

public interface AbstractDAO<ID, T extends Serializable> {

	public void setClazz(final Class<T> clazz);
	
	public T get(final ID id);
	
	public List<T> getAll();
	
	public void save(final T entity);
	
	public void update(final T entity);
	
	public void delete(final T entity);
	
	public void deleteById(final ID entityId);
}
