package uk.co.vurt.hakken.server.connector.activityassistant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.co.vurt.hakken.server.connector.AbstractDataConnector;
import uk.co.vurt.hakken.server.connector.DataConnectorTaskDefinition;

public class ActivityAssistantDataConnector extends AbstractDataConnector {

	private Map<String, DataConnectorTaskDefinition> definitions = new HashMap<String, DataConnectorTaskDefinition>();
//	private List<DataConnectorTaskDefinition> definitions = new ArrayList<DataConnectorTaskDefinition>();
	
	public ActivityAssistantDataConnector(){
		//TODO: Actually retrieve available definitions
		//in the mean time, hard code the HSC_WORKBOOK
		HSCWorkbookTaskDefinition hscDefinition = new HSCWorkbookTaskDefinition();
		definitions.put(hscDefinition.getName(), hscDefinition);
	}
	
	@Override
	public String getInstances(DataConnectorTaskDefinition taskDefinition,
			String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DataConnectorTaskDefinition> getDefinitions() {
		return (List<DataConnectorTaskDefinition>) definitions.values();
	}

	@Override
	public DataConnectorTaskDefinition getDefinition(String name) {
		return definitions.get(name);
	}

	@Override
	public boolean createNew() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean save() {
		// TODO Auto-generated method stub
		return false;
	}

}
