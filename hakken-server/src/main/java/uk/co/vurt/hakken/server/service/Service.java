package uk.co.vurt.hakken.server.service;

import java.io.Serializable;

import uk.co.vurt.hakken.server.persistence.GenericDAO;

public interface Service<ID, T extends Serializable> {

	public void setDao(GenericDAO<ID, T> dao);
}
