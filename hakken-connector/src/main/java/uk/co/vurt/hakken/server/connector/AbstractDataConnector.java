package uk.co.vurt.hakken.server.connector;



public abstract class AbstractDataConnector<T extends DataConnectorTaskDefinition> implements DataConnector<T> {

	private String name;
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}

	public String getType() {
		return this.getClass().getSimpleName();
	}
	
	
}
