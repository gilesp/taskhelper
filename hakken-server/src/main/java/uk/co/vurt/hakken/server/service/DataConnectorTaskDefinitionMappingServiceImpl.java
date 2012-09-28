package uk.co.vurt.hakken.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.vurt.hakken.server.connector.DataConnector;
import uk.co.vurt.hakken.server.connector.DataConnectorTaskDefinition;
import uk.co.vurt.hakken.server.mapping.DataConnectorTaskDefinitionMapping;
import uk.co.vurt.hakken.server.persistence.DataConnectorTaskDefinitionMappingDAO;

@Service
public class DataConnectorTaskDefinitionMappingServiceImpl implements
		DataConnectorTaskDefinitionMappingService {

	DataConnectorTaskDefinitionMappingDAO dao;
	
	@Override
	public DataConnectorTaskDefinitionMapping get(Long id) {
		return dao.get(id);
	}

	@Override
	public void save(DataConnectorTaskDefinitionMapping mapping) {
		dao.save(mapping);
	}

	@Override
	public DataConnectorTaskDefinitionMapping get(DataConnector connector,
			DataConnectorTaskDefinition definition) {
		return dao.get(connector.getName(), definition.getName());
	}

	@Override
	public List<DataConnectorTaskDefinitionMapping> getAll() {
		return dao.getAll();
	}

	@Autowired
	public void setDao(DataConnectorTaskDefinitionMappingDAO dao) {
		dao.setClazz(DataConnectorTaskDefinitionMapping.class);
		this.dao = dao;
	}
}
