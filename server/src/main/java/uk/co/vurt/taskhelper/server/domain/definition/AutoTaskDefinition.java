package uk.co.vurt.taskhelper.server.domain.definition;

import flexjson.JSON;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.ManyToMany;
import javax.persistence.OrderColumn;
import javax.validation.constraints.NotNull;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJson
@RooJpaActiveRecord(finders = { "findAutoTaskDefinitionsByNameLike" })
public class AutoTaskDefinition {

    @NotNull
    private String name;

    @NotNull
    private String description;

    @ManyToMany(cascade = CascadeType.ALL)
    @OrderColumn(name = "INDEX")
    private List<TaskPage> pages;

    @JSON(include = true)
    public List<uk.co.vurt.taskhelper.server.domain.definition.TaskPage> getPages() {
        return pages;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((pages == null) ? 0 : pages.hashCode());
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
		AutoTaskDefinition other = (AutoTaskDefinition) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (pages == null) {
			if (other.pages != null)
				return false;
		} else if (!pages.equals(other.pages))
			return false;
		return true;
	}
    
}
