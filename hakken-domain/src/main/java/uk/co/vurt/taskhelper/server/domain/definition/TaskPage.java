package uk.co.vurt.taskhelper.server.domain.definition;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ManyToMany;
import javax.persistence.OrderColumn;
import javax.validation.constraints.NotNull;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

import flexjson.JSON;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
@RooJson
public class TaskPage {

    @NotNull
    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    @OrderColumn(name="INDEX")
    private List<PageItem> items;

    @JSON
	public List<PageItem> getItems() {
		return items;
	}
    
}
