package uk.co.vurt.hakken.server.connector.db;

import java.util.Arrays;
import java.util.List;

import uk.co.vurt.hakken.server.connector.ConfigProperty;
import uk.co.vurt.hakken.server.connector.DataConnectorTaskDefinition;

public class DatabaseTableTaskDefinition extends DataConnectorTaskDefinition {

	static {
		configProperties = Arrays.asList(new ConfigProperty("username", true));
	}
	
	public DatabaseTableTaskDefinition(String name, List<String> dataItemNames){
		this.name = name;
		this.dataItemNames = dataItemNames;
	}
	
}
