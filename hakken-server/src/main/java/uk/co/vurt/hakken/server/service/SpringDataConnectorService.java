package uk.co.vurt.hakken.server.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import uk.co.vurt.hakken.server.connector.DataConnector;

public class SpringDataConnectorService implements DataConnectorService, ApplicationContextAware {

	private static final Logger logger = LoggerFactory.getLogger(SpringDataConnectorService.class);
	
	Map<String, DataConnector> dataConnectors;
	ApplicationContext context;
	
	public void findDataConnectors() {
		dataConnectors = context.getBeansOfType(DataConnector.class);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.context = applicationContext;
	}

	@Override
	public String[] getConnectorNames() {
		if(dataConnectors == null){
			findDataConnectors();
		}
		return dataConnectors.keySet().toArray(new String[0]);
	}

	@Override
	public List<DataConnector> getDataConnectors() {
		if(dataConnectors == null){
			findDataConnectors();
		}
		return new ArrayList<DataConnector>(dataConnectors.values());

	}

	@Override
	public DataConnector getDataConnector(String connectorName) {
		if(dataConnectors == null){
			findDataConnectors();
		}
		DataConnector connector = null;
		if(connectorName != null){
			connector = dataConnectors.get(connectorName);
		}
		return connector;
	}

	@Override
	public Map<String, DataConnector> getDataConnectorsMap() {
		if(dataConnectors == null){
			findDataConnectors();
		}
		return dataConnectors;
	}

}
