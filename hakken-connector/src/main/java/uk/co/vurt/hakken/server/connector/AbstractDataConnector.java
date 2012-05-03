package uk.co.vurt.hakken.server.connector;



public abstract class AbstractDataConnector implements DataConnector {

	private String name;
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
//	@Override
//	public String getInstances(DataConnectorTaskDefinition taskDefinition, String username){
//		return null;
//	}
//	
//	@Override
//	public List<DataConnectorTaskDefinition> getDefinitions(){
//		return new ArrayList<DataConnectorTaskDefinition>();
//	}
//	
//	@Override
//	public DataConnectorTaskDefinition getDefinition(String name) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public boolean createNew() {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public boolean save() {
//		// TODO Auto-generated method stub
//		return false;
//	}

}
