package uk.co.vurt.hakken.server.persistence.jpa;

import java.io.Serializable;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import uk.co.vurt.hakken.server.persistence.GenericDAO;

@Repository
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class GenericJpaDAO<ID, T extends Serializable> extends AbstractJpaDAO<ID, T> implements GenericDAO<ID, T> {

}
