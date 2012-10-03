package uk.co.vurt.hakken.server.persistence.jpa;

import org.springframework.stereotype.Repository;

import uk.co.vurt.hakken.server.mapping.ServiceMapping;
import uk.co.vurt.hakken.server.persistence.ServiceMappingDAO;

@Repository
public class ServiceMappingJpaDAO extends AbstractJpaDAO<Long, ServiceMapping>
		implements ServiceMappingDAO {

}
