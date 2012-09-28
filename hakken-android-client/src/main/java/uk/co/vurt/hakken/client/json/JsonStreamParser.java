package uk.co.vurt.hakken.client.json;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import uk.co.vurt.hakken.domain.job.JobDefinition;
import uk.co.vurt.hakken.domain.task.TaskDefinition;

public interface JsonStreamParser {

	public List<JobDefinition> parseJobDefinitionStream(InputStream in) throws IOException;
	
	public void parseJobDefinitionStream(InputStream in, JobDefinitionHandler callback) throws IOException;
	
	public List<TaskDefinition> parseTaskDefinitionStream(InputStream in) throws IOException;
	
	public void parseTaskDefinitionStream(InputStream in, TaskDefinitionHandler callback) throws IOException;	
}
