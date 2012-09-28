package uk.co.vurt.hakken.server.persistence.jpa;

import org.springframework.stereotype.Repository;

import uk.co.vurt.hakken.domain.job.DataItem;
import uk.co.vurt.hakken.server.persistence.DataItemDAO;

@Repository
public class DataItemJpaDAO extends AbstractJpaDAO<Long, DataItem> implements
		DataItemDAO {
}
