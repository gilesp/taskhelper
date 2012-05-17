package uk.co.vurt.hakken.server.persistence;

import uk.co.vurt.hakken.server.mapping.DataConnectorTaskDefinitionMapping;

public interface DataConnectorTaskDefinitionMappingDAO extends AbstractDAO<Long, DataConnectorTaskDefinitionMapping>{

	public DataConnectorTaskDefinitionMapping get(String dataConnectorName, String taskName);
}
