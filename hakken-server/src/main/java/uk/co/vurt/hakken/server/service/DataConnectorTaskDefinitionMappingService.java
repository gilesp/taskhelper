package uk.co.vurt.hakken.server.service;

import java.util.List;

import uk.co.vurt.hakken.server.connector.DataConnector;
import uk.co.vurt.hakken.server.connector.DataConnectorTaskDefinition;
import uk.co.vurt.hakken.server.mapping.DataConnectorTaskDefinitionMapping;

public interface DataConnectorTaskDefinitionMappingService extends Service<Long, DataConnectorTaskDefinitionMapping> {

	public List<DataConnectorTaskDefinitionMapping> getAll();
	
	public void save(DataConnectorTaskDefinitionMapping mapping);
	
	public DataConnectorTaskDefinitionMapping get(DataConnector connector, DataConnectorTaskDefinition definition);
}
