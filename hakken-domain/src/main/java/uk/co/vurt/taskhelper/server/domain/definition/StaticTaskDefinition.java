package uk.co.vurt.taskhelper.server.domain.definition;

import java.io.Serializable;


public class StaticTaskDefinition extends TaskDefinition implements Serializable{

	private static final long serialVersionUID = 7397304484991310361L;

    private String jsonDefinition;

    public String jsonify() {
        return jsonDefinition;
    }

	public String getJsonDefinition() {
		return jsonDefinition;
	}

	public void setJsonDefinition(String jsonDefinition) {
		this.jsonDefinition = jsonDefinition;
	}

    
}
