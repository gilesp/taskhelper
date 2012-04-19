package uk.co.vurt.hakken.server.persistence;

import java.io.Serializable;

public interface GenericDAO<ID, T extends Serializable> extends AbstractDAO<ID, T> {

}
