package uk.co.vurt.taskhelper.server.domain.definition;

import flexjson.JSONDeserializer;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJson(toJsonMethod = "jsonify")
@RooJpaActiveRecord(finders = { "findManualTaskDefinitionsByNameLike" })
public class ManualTaskDefinition {

    @NotNull
    private String name;

    @NotNull
    @Size(max = 20000)
    private String jsonDefinition;

    public String jsonify() {
        return jsonDefinition;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((jsonDefinition == null) ? 0 : jsonDefinition.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ManualTaskDefinition other = (ManualTaskDefinition) obj;
		if (jsonDefinition == null) {
			if (other.jsonDefinition != null)
				return false;
		} else if (!jsonDefinition.equals(other.jsonDefinition))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
    
}
