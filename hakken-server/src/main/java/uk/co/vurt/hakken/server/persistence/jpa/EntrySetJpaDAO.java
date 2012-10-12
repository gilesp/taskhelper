package uk.co.vurt.hakken.server.persistence.jpa;

import org.springframework.stereotype.Repository;

import uk.co.vurt.hakken.server.mapping.EntrySet;
import uk.co.vurt.hakken.server.persistence.EntrySetDAO;

@Repository
public class EntrySetJpaDAO extends AbstractJpaDAO<Long, EntrySet> implements
		EntrySetDAO {
}
