package uk.co.vurt.hakken.server.service;

import java.util.List;
import java.util.Map;

import uk.co.vurt.hakken.server.connector.DataConnector;


public interface DataConnectorService {

	public String[] getConnectorNames();
	public List<DataConnector> getDataConnectors();
	public Map<String, DataConnector> getDataConnectorsMap();
	public DataConnector getDataConnector(String connectorName);
	
}
