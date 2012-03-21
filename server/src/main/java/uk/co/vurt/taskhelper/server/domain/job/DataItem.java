package uk.co.vurt.taskhelper.server.domain.job;

import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
@RooJson
public class DataItem {
	
	private String pageName;
	
	private String name;
	
	private String type;
	
	@Size(max = 4000)
	private String value;
	
	@ManyToOne
	private Job job;
	
}
