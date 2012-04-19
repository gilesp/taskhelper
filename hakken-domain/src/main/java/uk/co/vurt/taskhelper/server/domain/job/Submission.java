package uk.co.vurt.taskhelper.server.domain.job;

import java.util.Set;

import javax.persistence.OneToMany;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
@RooJson
public class Submission {
	

	private String username;
	
	private int jobId;
	
	@OneToMany
	private Set<DataItem> dataitems;
	
}
