package uk.co.vurt.hakken.server.service;

import java.io.Serializable;

import org.springframework.transaction.annotation.Transactional;

import uk.co.vurt.hakken.server.persistence.AbstractDAO;

public interface Service<ID, T extends Serializable> {

	@Transactional(readOnly = true)
	public T get(ID id);

}
