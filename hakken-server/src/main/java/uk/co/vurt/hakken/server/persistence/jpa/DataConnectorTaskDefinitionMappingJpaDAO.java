package uk.co.vurt.hakken.server.persistence.jpa;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import uk.co.vurt.hakken.server.mapping.DataConnectorTaskDefinitionMapping;
import uk.co.vurt.hakken.server.persistence.DataConnectorTaskDefinitionMappingDAO;

@Repository
public class DataConnectorTaskDefinitionMappingJpaDAO extends
		AbstractJpaDAO<Long, DataConnectorTaskDefinitionMapping> implements DataConnectorTaskDefinitionMappingDAO {

	@Override
	@Transactional(readOnly = true)
	public DataConnectorTaskDefinitionMapping get(String dataConnectorName,
			String taskName) {
		Query query = entityManager.createQuery("from " + clazz.getName() + " where dataConnectorName = :dataConnectorName and taskDefinitionName = :taskName");
		query.setParameter("dataConnectorName", dataConnectorName);
		query.setParameter("taskName", taskName);
		List<DataConnectorTaskDefinitionMapping> mappings = query.getResultList();
		if(mappings.size() > 0){
			return mappings.get(0);
		}else {
			return null;
		}
	}
}
