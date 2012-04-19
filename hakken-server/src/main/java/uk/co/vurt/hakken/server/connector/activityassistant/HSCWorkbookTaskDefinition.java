package uk.co.vurt.hakken.server.connector.activityassistant;

import java.util.HashMap;

import uk.co.vurt.hakken.server.connector.DataConnectorTaskDefinition;

public class HSCWorkbookTaskDefinition extends DataConnectorTaskDefinition {
	
	static {
		name = "HSC_WORKBOOK";
		dataitemNameMap = new HashMap<String, String>();
		dataitemNameMap.put("REPORT.RR_ID", "");
		dataitemNameMap.put("HSA_YOUTH_SERVICES_CONSENT.HSC", "youth_services_consent"); //could store path as well, i.e. page_name|data_item_name
	}
	
	public HSCWorkbookTaskDefinition(){}
	
}
